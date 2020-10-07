package game.item;

import game.Player;
import game.Scene;

public class ItemLegendaryRock extends Item
{

    @Override
    public String getItemID()
    {
        return "legendary_rock";
    }

    @Override
    public String getItemName()
    {
        return "Legendary Rock";
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
