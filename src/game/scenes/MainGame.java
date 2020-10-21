package game.scenes;

import game.*;
import game.item.Item;
import game.item.ItemCrowbar;
import game.item.ItemKnife;
import game.item.ItemShovel;

import java.util.ArrayList;

public class MainGame extends Scene
{

    public Game game = null;

    public MainGame(Game game)
    {
        this.game = game;
    }

    @Override
    public void start()
    {

        String logo = Helpers.readTextFile("logo.txt");
        for (String line : logo.replace("\r", "").split("\n"))
        {
            System.out.println(line);
            //Helpers.sleep(100);
        }

        Options.add("Exit");
        Options.add("Inventory");
        Options.add("Debug");
        System.out.printf(Helpers.Localise("gameStartText"), "F");
        System.out.println();
    }

    @Override
    public void showDetails()
    {
        if (game.map.isGameCompleted)
        {
            // Game Completed
            exitScene();
        }

        UpdateDirectionOptions();
        showPositionMessage();
        showDirectionOptions();
        game.map.setLocationVisited(game.player);
        if (game.player.hasItem("shovel") && !Options.contains("Shovel"))
            Options.add("Shovel");
    }

    @Override
    public boolean handleCommand(String command, String... args)
    {
        // Get direction from Text
        Direction direction = convertDirections(command);
        if (direction != null)
        {
            processDirection(direction);
            return true;
        }

        switch (command)
        {
            case "exit":
                System.out.println(Helpers.Localise("exitingGame"));
                exitScene();
                break;
            case "go":
                if (args.length == 0)
                    break;

                // Get direction from Text
                direction = convertDirections(args[0]);
                if (direction != null)
                {
                    processDirection(direction);
                }else
                {
                    System.out.println(Helpers.Localise("unknown_direction"));
                }
                //System.out.printf("%x, %x: %s\n", game.player.positionX, game.player.positionY, game.map.mapData[game.player.positionX][game.player.positionY]);
                break;
            case "inventory":
                showInventory(game.player);
                break;
            case "shovel":
                if (game.player.hasItem("shovel"))
                    game.map.processMapTile(game.player, Action.Dig);
                return false;
            case "debug":
                if (!game.player.hasItem("knife"))
                {
                    ItemKnife knife = new ItemKnife();
                    ItemCrowbar crowbar = new ItemCrowbar();
                    ItemShovel shovel = new ItemShovel();
                    knife.enchanted = true;
                    game.player.inventory.add(knife);
                    game.player.inventory.add(crowbar);
                    game.player.inventory.add(shovel);
                    game.player.money = 100000;
                }
                break;
            default:
                return false;
        }
        return true;
    }

    public void showInventory(Player player)
    {
        ArrayList<String> names = new ArrayList<String>();
        for (Item item : player.inventory)
            names.add(item.getItemName());
        String[] namesArray = new String[names.size()];
        names.toArray(namesArray);
        System.out.println("Inventory: " + String.join(", ", namesArray));
        System.out.println("Money: " + player.money);
    }

    public void processDirection(Direction direction)
    {
        // Check if its valid
        if (checkValidDirection(direction))
        {
            // Check direction
            switch (direction)
            {
                case North:
                    --game.player.positionY;
                    break;
                case East:
                    ++game.player.positionX;
                    break;
                case South:
                    ++game.player.positionY;
                    break;
                case West:
                    --game.player.positionX;
                    break;
            }
            game.map.processMapTile(game.player, Action.Walk);
        }else
        {
            System.out.println(Helpers.Localise("invalid_direction"));
        }
    }

    public void showPositionMessage()
    {
        char row = (char)(game.player.positionX + 'a');
        String location = row + "" + (game.player.positionY + 1);
        if (game.map.getLocationVisited(game.player))
            System.out.println(Helpers.Localise(row + "" + (game.player.positionY + 1) + "_revisiting"));
        else
            System.out.println(Helpers.Localise(row + "" + (game.player.positionY + 1) + "_first_time"));
    }

    public void showDirectionOptions()
    {
        String message = "Would you like to head ";
        Direction[] directions = getDirections(game.map.mapCollisions[game.player.positionX][game.player.positionY]);
        for (int i = 0; i < directions.length; ++i)
        {
            if (i != 0)
            {
                if (i == directions.length - 1)
                    message += " or ";
                else
                    message += ", ";
            }
            message += directions[i].toString();
        }
        System.out.println("* " + message + "?");
    }

    public void UpdateDirectionOptions()
    {
        // Remove Go options
        for (int i = 0; i < 4; ++i)
            Options.remove("Go " + Direction.values()[i]);

        // Add Go options
        for (Direction direction : getDirections(game.map.mapCollisions[game.player.positionX][game.player.positionY]))
        {
            Options.add("Go " + direction);
        }
    }

    // Converts direction bits to an array of directions
    public Direction[] getDirections(int bits)
    {
        ArrayList<Direction> directions = new ArrayList<Direction>();
        for (int i = 0; i < 4; ++i)
        {
            if ((bits & (1 << i)) == 0)
                directions.add(Direction.values()[i]);
        }
        Direction[] directionsArray = new Direction[directions.size()];
        return directions.toArray(directionsArray);
    }

    // Converts text to direction
    public Direction convertDirections(String text)
    {
        switch (text.toLowerCase())
        {
            case "left":
            case "l":
            case "west":
            case "w":
                return Direction.West;
            case "right":
            case "r":
            case "e":
            case "east":
                return Direction.East;
            case "up":
            case "u":
            case "n":
            case "north":
                return Direction.North;
            case "down":
            case "d":
            case "s":
            case "south":
                return Direction.South;
            default:
                break;
        }
        // Invalid direction
        return null;
    }

    // Converts string to a valid direction
    public boolean checkValidDirection(Direction dir)
    {
        for (Direction direction : getDirections(game.map.mapCollisions[game.player.positionX][game.player.positionY]))
        {
            if (dir == direction)
                return true;
        }
        return false;
    }

    // Force any option
    @Override
    public boolean isOptionValid(String option)
    {
        return true;
    }
}
