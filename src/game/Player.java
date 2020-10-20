package game;

import game.item.Item;

import java.util.ArrayList;

public class Player
{

    public double money = 0;
    public int positionX = 0;
    public int positionY = 4;
    public int health = 1000;

    public ArrayList<Item> inventory = new ArrayList<Item>();

    public boolean hasItem(String name)
    {
        for (Item item : inventory)
        {
            if (item.getItemID().equals(name))
                return true;
        }
        return false;
    }

    public Item getItem(String name)
    {
        for (Item item : inventory)
        {
            if (item.getItemID().equals(name))
                return item;
        }
        return null;
    }

    public void init()
    {
        money = 0;
        positionX = 0;
        positionY = 4;
        health = 1000;
    }
}
