package game;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;

public class Helpers
{

    public static HashMap<String, String> TextMap = new HashMap<String, String>();

    public static void Start()
    {
        loadTextMap("en-au.txt");
    }

    public static void loadTextMap(String path)
    {
        for (String line : readTextFile(path).replace("\r", "").split("\n"))
        {
            TextMap.put(line.substring(0, line.indexOf("=")), line.substring(line.indexOf("=") + 1).replace("\\n", "\n"));
        }
    }

    public static String Localise(String key)
    {
        return TextMap.get(key);
    }

    public static String readTextFile(String path)
    {
        String result = "";
        try
        {
            BufferedReader in = new BufferedReader(new FileReader(path));
            String line = in.readLine();
            while(line != null)
            {
                if (result.length() != 0)
                    result += "\n" + line;
                else
                    result += line;
                line = in.readLine();
            }
            in.close();
        }catch (Exception ex)
        {
            System.err.println("ERROR");
            ex.printStackTrace();
        }
        return result;
    }

    public static void sleep(int mils)
    {
        try
        {
            Thread.sleep(mils);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }

}
