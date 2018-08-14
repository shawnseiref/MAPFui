package backEnd.Game;

import backEnd.Agents.Agent;
import backEnd.Agents.AgentSolution;
import backEnd.MapGenerators.Map;
import backEnd.MapGenerators.Position;
import backEnd.MapGenerators.StringMapGenerator;
import backEnd.Solvers.Solution;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class SubScenario {
    private Map map;
    private ArrayList<Agent> agentsList;
    private HashMap<Position,Agent> agents;
    private int nextAgentID;
    private Solution sol;

    public SubScenario(Map map) {
        this.map = map;
        this.agents = new HashMap();
        agentsList=new ArrayList<>();
        this.nextAgentID = 0;
    }

    public ArrayList<Agent> getAgentsList() {
        return agentsList;
    }

    public SubScenario(Map map, HashMap<Position, Agent> agents, Solution sol) {
        this.map = map;
        this.agents = agents;
        agentsList=new ArrayList<>();
        this.sol = sol;
    }

    public SubScenario(Map map, HashMap<Position, Agent> agents) {
        this.map = map;
        this.agents = agents;
        agentsList=new ArrayList<>();
        nextAgentID=agents.size();
    }

    public SubScenario(Map map,File file) {
        this.map=map;
        String game=fileToStr(file);
        nextAgentID=0;
        Agent newOne=new Agent(nextAgentID,null,null);
        AgentSolution newOneSol=new AgentSolution();
        agentsList.add(newOne);
        Position nextPos=new Position();
        for(int i=0;i<game.length();i++){
            if(game.charAt(i)=='.'){
                agentsList.get(nextAgentID).setGoalLocation(nextPos);
                sol.addSolution(newOneSol);
                nextAgentID++;
                i++;
                if(game.length()==i)
                    break;
            }
            if(game.charAt(i)=='\n'){
                newOne=new Agent(nextAgentID,null,null);
                agentsList.add(newOne);//check if it updates afterwards.
                newOneSol=new AgentSolution();
                while(game.charAt(i)!='(')
                    i++;
            }
            i++;
            int x=0;
            while(game.charAt(i)!=','){
                x = x * 10 + Character.getNumericValue(game.charAt(i));
                i++;
            }
            i++;
            int y=0;
            while(game.charAt(i)!=')'){
                y = y * 10 + Character.getNumericValue(game.charAt(i));
                i++;
            }
            nextPos=new Position(x,y);
            if(agentsList.get(nextAgentID).getLocation()==null){
                agentsList.get(nextAgentID).setLocation(nextPos);//check if really changed
                agents.put(agentsList.get(nextAgentID).getLocation(),newOne);
            }
            newOneSol.addPosition(nextPos);
        }
    }

    public Solution getSol() {
        return sol;
    }

    public int getNextAgentID() {
        return nextAgentID;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public HashMap<Position, Agent> getAgents() {
        return agents;
    }

    public void setAgents(HashMap<Position, Agent> agents) {
        this.agents = agents;
    }

    public void addAgent(Agent agent){
        agents.put(agent.getLocation(),agent);
        agentsList.add(agent);
        nextAgentID++;
    }

    public void removeAgent(Agent agent){
        agents.remove(agent.getLocation());
    }

    public void moveAgent(Position current, Position target){
        agents.put(target,agents.get(current));
        agents.remove(current);
    }

    private String fileToStr(File file){
        Scanner in=null;
        try {
            in = new Scanner((file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        while(in.hasNext()) {
            sb.append(in.next());
            sb.append('\n');
        }
        in.close();
        return sb.toString();
    }
}
