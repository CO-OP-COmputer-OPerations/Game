package game.scenes;

import game.Game;
import game.Helpers;
import game.Scene;

public class MainMenu extends Scene
{

    @Override
    public void showDetails()
    {
    }

    @Override
    public boolean handleCommand(String command, String... args)
    {
        switch (command)
        {
            case "start":
                Scene.startScene(new MainGame(new Game()));
                System.out.println(Helpers.readTextFile("mainMenu.txt"));
                break;
            case "instructions":
                System.out.println(Helpers.readTextFile("instructions.txt"));
                break;
            case "exit":
                exitScene();
                break;
            default:
               return false;
        }
        return true;
    }

    @Override
    public void start()
    {
        System.out.println(Helpers.readTextFile("mainMenu.txt"));
        Options.add("Start");
        Options.add("Instructions");
        Options.add("Exit");
    }
}
