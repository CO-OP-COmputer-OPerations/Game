package game.scenes;

import game.Direction;
import game.Game;
import game.Helpers;
import game.Scene;

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
        System.out.printf(Helpers.Localise("gameStartText"), "F");
        System.out.println();
    }

    @Override
    public void showDetails()
    {
        UpdateDirectionOptions();
    }

    @Override
    public boolean handleCommand(String command, String... args)
    {
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
                Direction direction = ConvertDirections(args[0]);
                if (direction != null)
                {
                    // Check if its valid
                    if (CheckValidDirection(direction))
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
                    }else
                    {
                        System.out.println(Helpers.Localise("invalid_direction"));
                    }
                }else
                {
                    System.out.println(Helpers.Localise("unknown_direction"));
                }
                System.out.printf("%x, %x: %s\n", game.player.positionX, game.player.positionY, game.map.mapData[game.player.positionX][game.player.positionY]);
                break;
            default:
                return false;
        }
        return true;
    }

    public void UpdateDirectionOptions()
    {
        // Remove Go options
        for (int i = 0; i < 4; ++i)
            Options.remove("Go " + Direction.values()[i]);

        // Add Go options
        for (Direction direction : GetDirections(game.map.mapCollisions[game.player.positionX][game.player.positionY]))
        {
            Options.add("Go " + direction);
        }
    }

    // Converts direction bits to an array of directions
    public Direction[] GetDirections(int bits)
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
    public Direction ConvertDirections(String text)
    {
        switch (text)
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
        return null;
    }

    // Converts string to a valid direction
    public boolean CheckValidDirection(Direction dir)
    {
        for (Direction direction : GetDirections(game.map.mapCollisions[game.player.positionX][game.player.positionY]))
        {
            if (dir == direction)
                return true;
        }
        return false;
    }
}
