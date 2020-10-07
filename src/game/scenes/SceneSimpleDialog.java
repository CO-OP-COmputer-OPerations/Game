package game.scenes;

import game.Game;
import game.Helpers;
import game.Scene;

import java.util.Arrays;

public class SceneSimpleDialog extends Scene
{
    public String messageKey;
    public String answer = "none";
    public String[] options;

    public SceneSimpleDialog(String messageKey, String[] options)
    {
        this.messageKey = messageKey;
        this.options = options;
    }

    public static String OpenDialog(SceneSimpleDialog scene)
    {
        Scene.StartScene(scene);
        return scene.answer;
    }

    @Override
    public void start()
    {
        System.out.println(Helpers.Localise(messageKey));
        Options.addAll(Arrays.asList(options));
    }

    @Override
    public void showDetails()
    {

    }

    @Override
    public boolean handleCommand(String command, String... args)
    {
        answer = command;
        exitScene();
        return true;
    }
}
