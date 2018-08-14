package backEnd.MapGenerators;

public class Map {

    private char [][] map;
    private int height;
    private int width;

    public Map(char[][] map) {
        this.map = map;
        height=map.length;
        width=map[0].length;
    }

    public char[][] getGrid() {
        return map;
    }

    public void setGrid(char[][] map) {
        this.map = map;
    }

    public boolean posExists(Position position){
        return posExists(position.getX(), position.getY());
    }

    public boolean posExists(int x, int y){
        return map!=null && map[0]!=null && x>=0 && x<map.length && y>=0 && y<map[0].length;
    }
    public boolean posReachable(Position position){
        return posReachable(position.getX(),position.getY());
    }

    public  boolean posReachable(int x, int y){
        return (posExists(x,y) && (map[x][y]=='.' || map[x][y]=='G'));//check not wall settings
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

}
