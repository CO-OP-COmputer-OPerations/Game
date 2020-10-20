package game.scenes;

import game.Helpers;
import game.Player;
import game.Scene;

import java.util.Random;

public class SceneBattleBear extends Scene
{

    public boolean battleCompleted = false;
    public Player player;

    // Bear data. It's stored here as it doesn't need to be persistent
    public int bearHealth = 500;

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
        Options.add("Debug");

        System.out.printf(Helpers.Localise("battle_start"), "The Big Flipping Radical Bear", getOptionsString());
        showDetails();
    }

    @Override
    public void showDetails()
    {
        if (bearHealth <= 0)
        {
            battleCompleted = true;
            System.out.printf(Helpers.Localise("battle_dead"), "The Big Flipping Radical Bear");
            exitScene();
        }
        if (player.health <= 0)
        {
            System.out.println(Helpers.Localise("battle_fail"));
            player.health = 100;
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
                    int amount = 0;
                    switch (args[0])
                    {
                        case "knife":
                            amount = new Random().nextInt(250) + 100;
                            bearHealth -= amount;
                            System.out.printf(Helpers.Localise("battle_knife"), "The Big Flipping Radical Bear", amount, bearHealth);
                            break;
                        case "fist":
                            amount = new Random().nextInt(20) + 1;
                            bearHealth -= amount;
                            System.out.printf(Helpers.Localise("battle_fist"), "The Big Flipping Radical Bear", amount, bearHealth);
                            break;
                        default:
                            break;
                    }
                }
                int amount = new Random().nextInt(300) + 1;
                player.health -= amount;
                System.out.printf(Helpers.Localise("battle_hit"), "The Big Flipping Radical Bear", amount, player.health);
                return true;
            case "run":
                System.out.println(Helpers.Localise("battle_running"));
                exitScene();
                return true;
            case "debug":
                battleCompleted = true;
                exitScene();
                return true;
            default:
                break;
        }

        return false;
    }
}
