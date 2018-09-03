package backEnd.MapGenerators;

public class RandomMapGenerator extends AMapGenerator {
    private Map map;

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map.setWidth(map.getWidth());
        this.map.setHeight(map.getHeight());
        this.map.setGrid(map.getGrid());
    }

    @Override
    public Map generate(Object o) {
        double[] arr;
        if (o instanceof double[])
            arr = ((double[]) o);
        else
            return null;
        return new Map(createRandomMap(((int)arr[0]),((int)arr[1]),((float)arr[2])));
    }

    static char[][] createRandomMap(int rowsCount, int columnsCount, float obstacleProbability) {

        if (obstacleProbability > 1 || obstacleProbability < 0)
            throw new IllegalArgumentException("Value of obstacleProbability should be in the range - 0.0 to 1.0");

        char[][] map=new char[rowsCount+2][columnsCount+2];
        rowsCount+=2;
        columnsCount+=2;
        map=makeFrame(map);
        for(int i=1;i<map.length-1;i++)
            for(int j=1;j<map[0].length-1;j++)
                map[i][j]='.';
        int obstacleCount = 0, estimatedObstacleCount = (int)(rowsCount * columnsCount * obstacleProbability);
        outer: for (int i = 1; i < rowsCount-1; i++) {
            for(int j = i % 2+1; j < columnsCount-1; j+=2) {
                if(obstacleCount == estimatedObstacleCount)
                    break outer;
                if(Math.random() < obstacleProbability) {
                    map[j][i]='T';
                    obstacleCount++;
                }
            }
        }
        outer2: for (int i = 1; i < rowsCount-1; i++) {
            for(int j = (i + 1) % 2+1; j < columnsCount-1; j+=2) {
                if(obstacleCount == estimatedObstacleCount)
                    break outer2;
                if(Math.random() < obstacleProbability) {
                    map[j][i]='T';
                    obstacleCount++;
                }
            }
        }

        return map;
    }

    private static char[][] makeFrame(char[][] map) {
        for (int i = 0; i < map[0].length; i++) {
            map[0][i]='@';
            map[map.length-1][i]='@';
        }
        for (int i = 0; i < map.length; i++) {
            map[i][0]='@';
            map[i][map[0].length-1]='@';
        }
        return map;
    }
}
