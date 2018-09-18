//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package frontend.viewmodel;

import backEnd.Game.SubScenario;
import backEnd.MapGenerators.Map;
import backEnd.MapGenerators.Position;
import frontend.model.IModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class ViewModel extends Observable implements Observer {
    private IModel model;

    public ViewModel(IModel model) {
        this.model = model;
    }

    public void update(Observable o, Object arg) {
        if (o == this.model) {
            this.setChanged();
            this.notifyObservers();
        }

    }

    public Map getMap(IModel.Type type) {
        return model.getMap(type);
    }

    public void addAgent(Position start, Position goal,IModel.Type type) {
        model.addAgent(start, goal,type);
        setChanged();
        notifyObservers();
    }

    public void loadMap(File file,IModel.Type type) {
        model.generateMaze(file,type);
    }

    public void loadSol(File file) {
        model.loadSol(file);
    }

    public SubScenario getGame(IModel.Type type) {
        return model.getGame(type);
    }

    public void createFiles(String dirName) throws Exception {
        createDir(dirName);
        String mapStr=model.getMapStr();
        String agentsStr=model.getScensStr(dirName);
        File mapFile= new File("Created Files/"+dirName+"/"+dirName+".map");
        mapFile.createNewFile();
        writeToFile(mapFile,mapStr);
        if(model.getGame(IModel.Type.CREATE).getAgentsList().size()>0){
            File agentsFile= new File("Created Files/"+dirName+"/"+dirName+".scen");
            agentsFile.createNewFile();
            writeToFile(agentsFile,agentsStr);
        }
        setChanged();
        notifyObservers();
    }

    private void writeToFile(File file, String str) {
        try {
            FileWriter writer=new FileWriter(file);
            writer.write(str);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createDir(String dirName) throws Exception {
        File theDir = new File("Created Files/"+dirName);
        if (!theDir.exists()) {
            try{
                theDir.mkdir();
            }
            catch(SecurityException se){
            }
        }
        else {
            throw new Exception();
        }
    }

    public void moveState(int j,int i) {
        model.moveState(j,i);
        setChanged();
    }

    public void randomMap(double[] arr) {
        model.randomMap(arr);
    }

    public boolean validGoal(Position pos){
        return model.validGoal(pos);
    }

    public boolean validStart(Position pos){
        return model.validStart(pos);
    }
}
