package game.item;

import game.Player;
import game.Scene;

public class ItemCrowbar extends Item
{
    @Override
    public String getItemID()
    {
        return "crowbar";
    }

    @Override
    public String getItemName()
    {
        return "Crowbar";
    }

    @Override
    public int getItemValue()
    {
        return 120;
    }

    @Override
    public void useItem(Player player, Scene scene)
    {

    }
}
