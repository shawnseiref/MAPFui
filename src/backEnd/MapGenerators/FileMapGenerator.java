package backEnd.MapGenerators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;
import java.util.stream.Stream;

public class FileMapGenerator extends AMapGenerator {
    @Override
    public Map generate(Object o) {
        if(!(o instanceof File))
            return null;
//        Scanner in= null;
        BufferedReader br;
        StringBuilder sb = new StringBuilder();
        try {
            br = new BufferedReader(new FileReader((File)o));
            Stream<String> s = br.lines();
            s.forEach(s1 -> sb.append(s1).append("\n"));
//            in = new Scanner(((File)o));
//            while(in.hasNext()) {
//                sb.append(in.next()).append("\n");
//            }
//            in.close();
            if (((File)o).getName().endsWith("map")){
                return new StringMapGenerator().generate(sb.toString());
            } else if (((File)o).getName().startsWith("Instance")){
                return new InstanceMapGenerator().generate(sb.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();

//            in.close();
        }
        return new StringMapGenerator().generate(sb.toString());
    }
}
