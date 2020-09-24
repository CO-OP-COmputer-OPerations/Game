package game;

public class Map
{
    public Locations[][] mapData = new Locations[5][5];
    public int[][] mapCollisions = new int[5][5];
    public boolean[][] visitedLocations = new boolean[5][5];

    public enum Locations
    {
        Empty,
        Village,
        Shop,
        Goal,
        Fortune_Teller,
        Boss,
        Traveler,
        Bear,
        Wishing_Well,
        Rock
    }

    public void loadMap(String path)
    {
        String[] data = Helpers.readTextFile(path).replace("\r", "").split("\n");

        for (int y = 0; y < 5; ++y)
        {
            for (int x = 0; x < 5; ++x)
            {
                mapData[x][y] = Locations.values()[Integer.parseInt("" + data[y].charAt(x), 16)];
                visitedLocations[x][y] = false;
            }
        }
        for (int y = 0; y < 5; ++y)
        {
            for (int x = 0; x < 5; ++x)
            {
                mapCollisions[x][y] = Integer.parseInt("" + data[y + 5].charAt(x), 16);
            }
        }
    }

    public void setLocationVisited(Player player)
    {
        visitedLocations[player.positionX][player.positionY] = true;
    }

    public boolean getLocationVisited(Player player)
    {
        return visitedLocations[player.positionX][player.positionY];
    }
}
