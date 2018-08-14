package backEnd.MapGenerators;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileMapGenerator extends AMapGenerator {
    @Override
    public Map generate(Object o) {
        if(!(o instanceof File))
            return null;
        Scanner in= null;
        try {
            in = new Scanner(((File)o));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        while(in.hasNext()) {
            sb.append(in.next());
        }
        in.close();
        return new StringMapGenerator().generate(sb.toString());
    }
}
