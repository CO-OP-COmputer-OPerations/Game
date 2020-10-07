package game.item;

import game.Player;
import game.Scene;

public abstract class Item
{
    /*
     The name of the item. This needs to be unique
     */
    public abstract String getItemID();
    /*
    The name of the item. This will be shown
    */
    public abstract String getItemName();
    /*
    How much the item costs in the store
    */
    public abstract int getItemValue();
    /*
    Event for when the item is used in the map or in battle
    */
    public abstract void useItem(Player player, Scene scene);

}
