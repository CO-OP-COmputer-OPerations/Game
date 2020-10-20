package game;

import game.item.Item;
import game.item.ItemKnife;
import game.item.ItemLegendaryRock;
import game.scenes.SceneBattleBear;
import game.scenes.SceneShop;
import game.scenes.SceneSimpleDialog;
import game.scenes.SceneVillage;

public class Map
{
    public Locations[][] mapData = new Locations[5][5];
    public int[][] mapCollisions = new int[5][5];
    public boolean[][] visitedLocations = new boolean[5][5];

    public enum Locations
    {
        Empty,
        Village,
        Shop,
        Goal,
        Fortune_Teller,
        Boss,
        Traveler,
        Bear,
        Wishing_Well,
        Rock
    }

    public void loadMap(String path)
    {
        String[] data = Helpers.readTextFile(path).replace("\r", "").split("\n");

        for (int y = 0; y < 5; ++y)
        {
            for (int x = 0; x < 5; ++x)
            {
                mapData[x][y] = Locations.values()[Integer.parseInt("" + data[y].charAt(x), 16)];
                visitedLocations[x][y] = false;
            }
        }
        for (int y = 0; y < 5; ++y)
        {
            for (int x = 0; x < 5; ++x)
            {
                mapCollisions[x][y] = Integer.parseInt("" + data[y + 5].charAt(x), 16);
            }
        }
    }

    public void setLocationVisited(Player player)
    {
        visitedLocations[player.positionX][player.positionY] = true;
    }

    public boolean getLocationVisited(Player player)
    {
        return visitedLocations[player.positionX][player.positionY];
    }

    public void processMapTile(Player player, Action action)
    {
        if (action == Action.Walk)
        {
            switch (mapData[player.positionX][player.positionY])
            {
                case Village:
                    // Stuff
                    Scene.startScene(new SceneVillage(player));
                    break;
                case Shop:
                    // Buy things
                    Scene.startScene(new SceneShop(player));
                    break;
                case Goal:
                    // End game
                    break;
                case Fortune_Teller:
                    // Tell things
                    break;
                case Boss:
                    // Boss
                    break;
                case Bear:
                    if (!visitedLocations[player.positionX][player.positionY])
                    {
                        // Bear battle
                        SceneBattleBear battle = new SceneBattleBear(player);
                        Scene.startScene(battle);
                        // TODO: Try not hardcode positions
                        // Move down if the bear didn't die
                        if (!battle.battleCompleted)
                            ++player.positionY;
                        }
                    break;
                case Wishing_Well:
                    // Ask if the knife should be enchanted
                    if (SceneSimpleDialog.OpenDialog(new SceneSimpleDialog("wishing_well_entering", new String[]{"Yes", "No"})).equalsIgnoreCase("yes"))
                    {
                        // Get knife item
                        Item knife = player.getItem("knife");
                        // Check if the knife is really a knife object
                        if (knife instanceof ItemKnife)
                        {
                            if (((ItemKnife) knife).enchanted)
                                System.out.println(Helpers.Localise("wishing_well_revisit"));
                            else
                            {
                                ((ItemKnife) knife).enchanted = true;
                                System.out.println(Helpers.Localise("wishing_well_finished"));
                            }
                        }
                    }
                    else
                    {
                        System.out.println(Helpers.Localise("wishing_well_canceled"));
                    }
                    // TODO: Try not hardcode positions
                    // Move right if the bear didn't die
                    ++player.positionX;
                    break;
                case Rock:
                    // Rock
                    if (player.hasItem("legendary_rock"))
                    {
                        // Already dug the rock
                        System.out.println(Helpers.Localise("a"));
                    }
                    else
                    {
                        // Have not yet dug the rock
                        System.out.println(Helpers.Localise("b"));
                    }
                    break;
                default:
                    break;
            }
        }
        else if (action == Action.Dig)
        {
            if (mapData[player.positionX][player.positionY] == Locations.Rock)
            {
                if (!player.hasItem("legendary_rock") && player.hasItem("shovel"))
                    player.inventory.add(new ItemLegendaryRock());
            }
        }
    }
}
