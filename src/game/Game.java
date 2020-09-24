package game;

public class Game
{

    public Player player = new Player();
    public Map map = new Map();

    public Game()
    {
        player.init();
        map.loadMap("mapData.txt");
        Helpers.loadTextMap("mapMessages.txt");
    }


}
