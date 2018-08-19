package backEnd.MapGenerators;

public class RandomMapGenerator extends AMapGenerator {
    private Map map;

    public RandomMapGenerator(Map map) {
        this.map = map;
    }

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

        return map;
    }
}
