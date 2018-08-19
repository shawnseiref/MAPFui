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
        String mapStr="";
        if(o instanceof String )
            mapStr=((String )o);
        else
            return null;
        int i=0;
        int height = 0;
        int width = 0;
        int obstacleProbability;
        String num ="";
        while (i<mapStr.length()) {
            while (mapStr.charAt(i) != ',') {
                num = num + mapStr.charAt(i);
            }
            if (height==0){
                int x=0;
            }
            // height =
        }
        return map;
    }
}
