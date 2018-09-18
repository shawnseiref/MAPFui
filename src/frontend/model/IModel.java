package frontend.model;

import backEnd.Game.SubScenario;
import backEnd.MapGenerators.Map;
import backEnd.MapGenerators.Position;

import java.io.File;

public interface IModel {

    void randomMap(double[] arr);

    public enum Type {
        CREATE,SIMULATE
    }

    void loadSol(File file);
    void moveState(int j,int i);
    SubScenario getGame(Type type);
    Map getMap(Type type);
    void generateMaze(int width, int height,double percentage);
    void generateMaze(File str,Type type);
    void generateMaze(String str,Type type);
    void moveCharacter(Position current,Position target);
    void addAgent(Position start, Position goal,Type type);
    String getMapStr();
    String getScensStr(String name);
    boolean validStart(Position pos);
    boolean validGoal(Position pos);
}
