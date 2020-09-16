package game;

import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.Scanner;

public abstract class Scene
{

    public boolean running = false;
    public ArrayList<String> Options = new ArrayList<String>();
    public abstract void start();
    public abstract void showDetails();
    public abstract boolean handleCommand(String command, String... args);

    public static void StartScene(Scene scene)
    {
        Main.Scenes.add(scene);
        scene.run();
    }

    public void run()
    {
        Options.add("Options");
        running = true;
        start();
        while (running)
        {
            showDetails();
            System.out.print("> ");
            String input = Main.MainScanner.nextLine();
            if (input.length() > 0)
            {
                String command = input.toLowerCase();
                String[] args = new String[0];
                if (input.contains(" "))
                {
                    command = command.substring(0, command.indexOf(" "));
                    args = input.substring(command.length() + 1).split(" ");
                }
                if (command.equals("options"))
                {
                    System.out.println("Options: " + getOptionsString());
                    continue;
                }
                if (!isOptionValid(command) || !handleCommand(command, args))
                {
                    System.out.println(Helpers.Localise("unknown_command"));
                }
            }
        }
    }

    public void exitScene()
    {
        running = false;
        Main.Scenes.remove(this);
        System.out.println("Closed Scene!");
    }

    public boolean isOptionValid(String option)
    {
        for (String option_ : Options)
        {
            if (option_.contains(" "))
                option_ = option_.substring(0, option_.indexOf(" "));
            if (option.equalsIgnoreCase(option_))
                return true;
        }
        return false;
    }

    public String getOptionsString()
    {
        return String.join(", ", Options);
    }



}
