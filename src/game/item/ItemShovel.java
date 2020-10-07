package game.item;

import game.Player;
import game.Scene;

public class ItemShovel extends Item
{
    @Override
    public String getItemID()
    {
        return "shovel";
    }

    @Override
    public String getItemName()
    {
        return "Shovel";
    }

    @Override
    public int getItemValue()
    {
        return 1300;
    }

    @Override
    public void useItem(Player player, Scene scene)
    {

    }
}
