package backEnd.MapGenerators;


public class StringMapGenerator extends AMapGenerator{
    @Override
    public  Map generate(Object o) {
        int rowsNum=0;
        int colNum=0;
        String mapStr="";
        if(o instanceof String )
            mapStr=((String )o);
        else
            return null;
        int counter=0;
        counter=skipToNum(mapStr,counter);
        while(Character.isDigit(mapStr.charAt(counter))){
            rowsNum = rowsNum * 10 + Character.getNumericValue(mapStr.charAt(counter));
            counter++;
        }
        counter=skipToNum(mapStr,counter);
        while(Character.isDigit(mapStr.charAt(counter))){
            colNum=colNum*10+Character.getNumericValue(mapStr.charAt(counter));
            counter++;
        }
        counter=counter+3;
        char[][] map=new char[rowsNum][colNum];
        for(int rows=0;rows<rowsNum;rows++) {
            for (int cols = 0; cols < colNum; cols++) {
                map[rows][cols]=mapStr.charAt(counter);
                counter++;
            }
        }
        return new Map(map);
    }

    private int skipToNum(String mapStr, int counter) {
        while(!Character.isDigit(mapStr.charAt(counter))){
            counter++;
        }
        return counter;
    }
}
