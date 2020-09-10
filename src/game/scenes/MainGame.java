package game.scenes;

import game.Game;
import game.Helpers;
import game.Scene;

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
        Options.add("Exit");
        System.out.printf(Helpers.Localise("gameStartText"), game.player.CharClass.toString());
        System.out.println();
    }

    @Override
    public void showDetails()
    {

    }

    @Override
    public boolean handleCommand(String command, String[]... args)
    {
        switch (command)
        {
            case "exit":
                System.out.println(Helpers.Localise("exitingGame"));
                exitScene();
                break;
            default:
                return false;
        }
        return true;
    }
}
