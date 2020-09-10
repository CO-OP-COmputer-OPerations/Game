package game;

import game.scenes.MainMenu;

import java.util.ArrayList;
import java.util.Scanner;

public class Main
{
    public static ArrayList<Scene> Scenes = new ArrayList<Scene>();
    public static Scanner MainScanner = null;

    public static void main(String[] args)
    {
        Helpers.Start();
        MainScanner = new Scanner(System.in);
        Scenes.add(new MainMenu());
        Scenes.get(Scenes.size() - 1).run();
        MainScanner.close();
    }
}
