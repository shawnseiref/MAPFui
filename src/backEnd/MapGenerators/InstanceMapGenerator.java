package backEnd.MapGenerators;


public class InstanceMapGenerator extends AMapGenerator {

    @Override
    public Map generate(Object o) {

        String mapStr = "";
        if (o instanceof String)
            mapStr = ((String) o);
        else
            return null;
        String [] splited = mapStr.split("\n");
        String [] rowcol = splited[2].split(",");
        int rows = extractNumber(rowcol[0]);
        int cols = extractNumber(rowcol[1]);
        char [][] map = new char[rows][cols];
        for (int row = 0; row < rows && row+3<splited.length; row++) {
            for (int col = 0; col < cols; col++) {
                map [row][col] = splited[row+3].charAt(col);
            }
        }
        return new Map(map);
    }

    private int extractNumber(String num) {
        try {
            return Integer.parseInt(num);
        }catch (Exception e){
            System.out.println("Couldn't read Numbers in: \""+num+"\"" );
            e.printStackTrace();
        }
        return 0;
    }




   /* private char[][] cleanMap(char[][] map) {
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
    }*/


}
