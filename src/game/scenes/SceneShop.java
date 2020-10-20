package game.scenes;

import game.Game;
import game.Helpers;
import game.Player;
import game.Scene;
import game.item.Item;
import game.item.ItemCrowbar;
import game.item.ItemKnife;
import game.item.ItemShovel;

import java.util.Arrays;
import java.util.Random;

public class SceneShop extends Scene
{

    public Player player;
    public boolean visited = false;

    public SceneShop(Player player)
    {
        this.player = player;
    }

    @Override
    public void start()
    {
        System.out.println(Helpers.Localise(!visited ? "shop_visit_first_time" : "shop_visit_revisiting"));
        visited = true;
    }

    @Override
    public void showDetails()
    {
        Options.clear();
        System.out.println(Helpers.Localise("shop_options"));
        Options.add("Crowbar");
        Options.add("Shovel");
        Options.add("Leave");
    }

    @Override
    public boolean handleCommand(String command, String... args)
    {
        switch (command)
        {
            case "crowbar":
                buyItem("Crowbar", new ItemCrowbar());
                return true;
            case "shovel":
                buyItem("Shovel", new ItemShovel());
                return true;
            case "leave":
                exitScene();
                return true;
            default:
                break;
        }

        return false;
    }

    public void buyItem(String itemName, Item item)
    {
        if (player.hasItem(item.getItemID()))
        {
            System.out.printf(Helpers.Localise("shop_out_of_stock"), itemName + "s");
        }
        else
        {
            if (player.money >= item.getItemValue())
            {
                player.money -= item.getItemValue();
                player.inventory.add(item);
                System.out.printf(Helpers.Localise("shop_item_obtained"), itemName);
            }
            else
                System.out.println(Helpers.Localise("shop_poor_person"));
        }

    }
}
