package frontend.model;

import backEnd.Agents.Agent;
import backEnd.Agents.AgentSolution;
import backEnd.Game.SubScenario;
import backEnd.MapGenerators.*;
import backEnd.Solvers.Solution;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;

public class Model extends Observable implements IModel {

    private SubScenario simulateGame;
    private SubScenario createGame;
    private int currentSolState;


    @Override
    public void randomMap(double[] arr) { ;
        RandomMapGenerator gen = new RandomMapGenerator();
        createGame=new SubScenario(gen.generate(arr));
        currentSolState = 0;
        setChanged();
        notifyObservers();
    }

    @Override
    public void loadSol(File file) {
        Solution sol=new Solution(file);
        simulateGame=new SubScenario(simulateGame.getMap(),sol);
        currentSolState = 0;
        setChanged();
        notifyObservers();
    }

    @Override
    public void moveState(int current,int next) {
        if(next>=0 && next<simulateGame.getSol().getSolLength()){
            ArrayList<AgentSolution> sols=simulateGame.getSol().getAgentsSolutions();
            for(int i=0;i<sols.size();i++){
                simulateGame.moveAgent(i,sols.get(i).getPath().get(next));
            }
        }
    }

    @Override
    public SubScenario getGame(Type type) {
        if(type==Type.CREATE)
            return createGame;
        else
            return simulateGame;
    }

    @Override
    public Map getMap(Type type) {
        if(type==Type.CREATE)
            return createGame.getMap();
        else
            return simulateGame.getMap();    }

    @Override
    public void generateMaze(int width, int height, double percentage) {
        //stuff
        currentSolState = 0;
    }

    @Override
    public void generateMaze(File str,Type type) {
        FileMapGenerator gen = new FileMapGenerator();
        if(type==Type.CREATE)
            createGame = new SubScenario(gen.generate(str));
        else
            simulateGame = new SubScenario(gen.generate(str));
        currentSolState = 0;
        setChanged();
        notifyObservers();
    }

    @Override
    public void generateMaze(String str, Type type) {
        StringMapGenerator gen = new StringMapGenerator();
        if(type==Type.CREATE)
            createGame = new SubScenario(gen.generate(str));
        else
            simulateGame = new SubScenario(gen.generate(str));
        currentSolState = 0;
        setChanged();
        notifyObservers();
    }

    @Override
    public void moveCharacter(Position current, Position target) {
        simulateGame.moveAgent(current, target);
        setChanged();
        notifyObservers();
    }

    @Override
    public void addAgent(Position start, Position goal, Type type) {
        if (type == Type.CREATE)
            createGame.addAgent(new Agent(createGame.getNextAgentID(), start, goal));
        else
            simulateGame.addAgent(new Agent(simulateGame.getNextAgentID(), start, goal));
    }

    public String getMapStr(){
        String ans="";
        char[][] grid=createGame.getMap().getGrid();
        ans+="type octile\n";
        ans+="height "+grid.length+"\n";
        ans+="width "+grid[0].length+"\n";
        ans+="map\n";
        for(int i=0;i<grid.length;i++){
            for (int j=0;j<grid[0].length;j++){
                ans+=grid[j][i];
            }
            ans+="\n";
        }
        return ans;
    }

    public String getScensStr(String name){
        String ans="";
        ans+="version 1\n";
        ArrayList<Agent> agents=createGame.getAgentsList();
        for (Agent agent:agents) {
            ans+="0\t"+name+".map\t";
            ans+=createGame.getMap().getGrid().length+"\t";
            ans+=createGame.getMap().getGrid()[0].length+"\t";
            ans+=agent.getLocation().getX()+"\t";
            ans+=agent.getLocation().getY()+"\t";
            ans+=agent.getGoalLocation().getX()+"\t";
            ans+=agent.getGoalLocation().getY()+"\t";
            ans+=agent.getRealDistance()+"\n";
        }
        return ans;
    }

    public boolean validStart(Position pos){
        try{
            ArrayList<Agent> list=createGame.getAgentsList();
            for (Agent agent: list) {
                if(agent.getLocation().equals(pos)==true)
                    return false;
            }
        }
        catch (Exception e){
            return false;
        }
        return validLoc(pos);
    }

    public boolean validGoal(Position pos){
        try{
            ArrayList<Agent> list=createGame.getAgentsList();
            for (Agent agent: list) {
                if(agent.getGoalLocation().equals(pos)==true)
                    return false;
            }
        }
        catch (Exception e){
            return false;
        }
        return validLoc(pos);
    }

    private boolean validLoc(Position pos){
        int x=pos.getX();
        int y=pos.getY();
        try{
            char[][] grid=createGame.getMap().getGrid();
            if(grid[x][y]!='.')
                return false;
        }
        catch (Exception e){
            return false;
        }
        return true;
    }
}
