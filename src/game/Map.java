package game;

import game.item.Item;
import game.item.ItemKnife;
import game.item.ItemLegendaryRock;
import game.scenes.SceneSimpleDialog;

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
                    break;
                case Shop:
                    // Buy things
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
                    // Bear battle
                    break;
                case Wishing_Well:
                    // TODO: Add question key
                    // Ask if the knife should be enchanted
                    if (SceneSimpleDialog.OpenDialog(new SceneSimpleDialog("testthing", new String[]{"Yes", "No"})).equalsIgnoreCase("yes"))
                    {
                        System.out.println(Helpers.Localise("enchant_working"));
                        Helpers.sleep(1000);
                        // Get knife item
                        Item knife = player.getItem("knife");
                        // Check if the knife is really a knife object
                        if (knife instanceof ItemKnife)
                            ((ItemKnife) knife).enchanted = true;
                        System.out.println(Helpers.Localise("enchant_finished"));
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
                if (!player.hasItem("legendary_rock"))
                    player.inventory.add(new ItemLegendaryRock());
            }
        }
    }
}
