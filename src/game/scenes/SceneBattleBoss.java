package game.scenes;

import game.Helpers;
import game.Player;
import game.Scene;

import java.util.Random;

public class SceneBattleBoss extends Scene
{

    public boolean battleCompleted = false;
    public Player player;

    // Boss data. It's stored here as it doesn't need to be persistent
    public int bossHealth = 500000000;

    public SceneBattleBoss(Player player)
    {
        this.player = player;
    }

    @Override
    public void start()
    {
        if (player.hasItem("knife"))
            Options.add("Use Knife");
        if (player.hasItem("legendary_rock"))
            Options.add("Use Rock");
        Options.add("Use Fist");
        Options.add("Run");
        Options.add("Debug");

        scrollText("boss_dialogue_1");
        Helpers.sleep(1000);
        System.out.println(Helpers.readTextFile("boss.txt"));
        Helpers.sleep(1000);
        scrollText("boss_dialogue_2");
        System.out.printf(Helpers.Localise("battle_start"), "DIO", getOptionsString());
        showDetails();
    }

    @Override
    public void showDetails()
    {
        if (bossHealth <= 0)
        {
            // Should be impossible.
            battleCompleted = true;
            System.out.printf(Helpers.Localise("battle_dead"), "DIO");
            exitScene();
        }
        if (player.health <= 0)
        {
            System.out.println(Helpers.Localise("battle_fail"));
            player.health = 1000;
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
                            bossHealth -= amount;
                            System.out.printf(Helpers.Localise("battle_knife"), "DIO", amount, bossHealth);
                            break;
                        case "fist":
                            amount = new Random().nextInt(20) + 1;
                            bossHealth -= amount;
                            System.out.printf(Helpers.Localise("battle_fist"), "DIO", amount, bossHealth);
                            break;
                        case "rock":
                            bossHealth = 0;
                            battleCompleted = true;
                            scrollText("boss_stone_used");
                            exitScene();
                            return true;
                        default:
                            break;
                    }
                }
                int amount = new Random().nextInt(3000) + 1;
                player.health -= amount;
                if (player.health < 0)
                    player.health = 0;
                System.out.printf(Helpers.Localise("battle_hit"), "DIO", amount, player.health);
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

    public void scrollText(String key)
    {
        String text = Helpers.Localise(key);
        for (String line : text.replace("\r", "").split("\n"))
        {
            System.out.println(line);
            Helpers.sleep(1750);
        }

    }
}
