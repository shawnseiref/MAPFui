package backEnd.MapGenerators;


public class StringMapGenerator extends AMapGenerator {
    @Override
    public Map generate(Object o) {
        int rowsNum = 0;
        int colNum = 0;
        String mapStr = "";
        if (o instanceof String)
            mapStr = ((String) o);
        else
            return null;
        int counter = 0;
        counter = skipToNum(mapStr, counter);
        rowsNum = getNum(mapStr, counter);
        counter += (int) Math.log10(rowsNum) + 1;
        counter = skipToNum(mapStr, counter);
        colNum = getNum(mapStr, counter);
        counter += Math.log10(colNum) + 4;
        char[][] map = new char[colNum][rowsNum];
        for (int rows = 0; rows < rowsNum; rows++) {
            for (int cols = 0; cols < colNum; cols++) {
                map[cols][rows] = mapStr.charAt(counter);
                counter++;
            }
        }
        map = cleanMap(map);
        return new Map(map);
    }

    private char[][] cleanMap(char[][] map) {
        int row = 0, col = 0;
        boolean isVoidcol = true, isLastVoidcol = false, isVoidrow = true, isLastVoidrow = false;
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                if (map[i][j]!='@' && map[i][j]!='O'){
                    isVoidcol=false;
                    isVoidrow=false;
                }
            }
        }
        return map;
    }

    private int getNum(String str, int counter) {
        int res = 0;
        while (Character.isDigit(str.charAt(counter))) {
            res = res * 10 + Integer.parseInt("" + str.charAt(counter));
            counter++;
        }
        return res;
    }

    private int skipToNum(String mapStr, int counter) {
        while (!Character.isDigit(mapStr.charAt(counter))) {
            counter++;
        }
        return counter;
    }


}
