package game;

public class Player
{

    public enum Classes
    {
        Mage,
        Knight,
        Student
    }

    public int Health = 1000;
    public int Attack = 0;
    public int Defence = 0;
    public Classes CharClass = Classes.Mage;

    public void init()
    {
        Health = 1000;
        Attack = 25;
        Defence = 25;
    }

    public int GetAttack()
    {
        int newAttack = 0;
        switch (CharClass)
        {
            case Mage:
                newAttack = 50;
                break;
            case Knight:
                newAttack = 25;
                break;
            case Student:
                newAttack = 30;
                break;
            default:
                break;
        }
        return Attack + newAttack;
    }

}
