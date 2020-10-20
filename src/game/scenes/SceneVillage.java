package game.scenes;

import game.Game;
import game.Helpers;
import game.Player;
import game.Scene;
import game.item.Item;
import game.item.ItemKnife;

import java.util.Arrays;
import java.util.Random;

public class SceneVillage extends Scene
{

    enum SceneVillageMenu
    {
        Main, Store, Vault
    }

    public Player player;
    public boolean visited = false;
    public boolean visitedStore = false;
    public boolean visitedVault = false;
    public boolean visitedVault2 = false;
    public SceneVillageMenu currentMenu = SceneVillageMenu.Main;

    public SceneVillage(Player player)
    {
        this.player = player;
    }

    @Override
    public void start()
    {
        System.out.println(Helpers.Localise(!visited ? "village_visit_first_time" : "village_visit_revisiting"));
        visited = true;
    }

    @Override
    public void showDetails()
    {
        Options.clear();
        switch (currentMenu)
        {
            case Main:
                System.out.println(Helpers.Localise("village_main_options"));
                Options.add("Store");
                Options.add("Vault");
                Options.add("Leave");
                break;
            case Store:
                System.out.println(Helpers.Localise("village_store_options"));
                Options.add("Knife");
                Options.add("Punch");
                Options.add("Leave");
                break;
            case Vault:
                Options.add("Leave");
                break;
        }
    }

    @Override
    public boolean handleCommand(String command, String... args)
    {
        switch (command)
        {
            case "store":
                System.out.println(Helpers.Localise(!visitedStore ? "village_store_first_time" : "village_store_revisiting"));
                visitedStore = true;
                // Give knife if you don't have one
                if (!player.hasItem("knife"))
                    player.inventory.add(new ItemKnife());
                return true;
            case "vault":
                // Get knife item
                Item knife = player.getItem("knife");
                // Check if the knife is really a knife object
                if (knife instanceof ItemKnife && ((ItemKnife) knife).enchanted)
                {
                    System.out.println(Helpers.Localise(!visitedVault2 ? "village_vault_first_time2" : "village_vault_revisiting2"));
                    if (!visitedVault2)
                    {
                        player.money += 1000000.45;
                        System.out.printf(Helpers.Localise("money_message"), player.money);
                    }
                    visitedVault2 = true;
                }else
                {
                    System.out.println(Helpers.Localise(!visitedVault ? "village_vault_first_time" : "village_vault_revisiting"));
                    visitedVault = true;
                }
                return true;
            case "leave":
                --player.positionX;
                exitScene();
                return true;
            default:
                break;
        }

        return false;
    }
}
