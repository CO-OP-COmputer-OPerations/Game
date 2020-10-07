package game.item;

import game.Player;
import game.Scene;

public class ItemKnife extends Item
{

    public boolean enchanted = false;

    @Override
    public String getItemID()
    {
        return "knife";
    }

    @Override
    public String getItemName()
    {
        return enchanted ? "Enchanted Knife" : "Knife";
    }

    @Override
    public int getItemValue()
    {
        return -1;
    }

    @Override
    public void useItem(Player player, Scene scene)
    {

    }
}
