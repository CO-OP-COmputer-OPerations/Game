package game.scenes;

import game.Game;
import game.Helpers;
import game.Player;
import game.Scene;

import java.util.Arrays;
import java.util.Random;

public class SceneBattleBear extends Scene
{

    public boolean IsRunningAway = false;
    public Player player;

    // Bear data. It's stored here as it doesn't need to be persistent
    public int bearHealth = 100;

    public SceneBattleBear(Player player)
    {
        this.player = player;
    }

    @Override
    public void start()
    {
        if (player.hasItem("knife"))
            Options.add("Use Knife");
        Options.add("Use Fist");
        Options.add("Run");
    }

    @Override
    public void showDetails()
    {
        if (bearHealth <= 0)
        {
            System.out.printf(Helpers.Localise("battle_dead"), "Bear");
            exitScene();
        }
    }


    @Override
    public boolean handleCommand(String command, String... args)
    {
        switch (command)
        {
            case "use":
                if (args.length > 0)
                {
                    switch (args[0])
                    {
                        case "knife":
                            return true;
                        case "fist":
                            int amount = new Random().nextInt(3) + 1;
                            bearHealth -= amount;
                            System.out.printf(Helpers.Localise("battle_fist"), "Bear", amount, bearHealth);
                            return true;
                        default:
                            break;
                    }
                }
                break;
            case "run":
                IsRunningAway = true;
                System.out.println(Helpers.Localise("battle_running"));
                exitScene();
                return true;
            default:
                break;
        }

        return false;
    }
}
