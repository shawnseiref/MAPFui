package backEnd.Solution;

import backEnd.Agents.Agent;
import backEnd.Agents.AgentSolution;
import backEnd.MapGenerators.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution {
    private int solLength;
    private ArrayList<AgentSolution> agentsSolutions;

    public Solution() {
        agentsSolutions = new ArrayList();
        solLength=0;
    }

    public ArrayList<AgentSolution> getAgentsSolutions() {
        return agentsSolutions;
    }

    public Solution(ArrayList<AgentSolution> agentsSolutions) {
        this.agentsSolutions = agentsSolutions;
        solLength=0;
        for(int i=0;i<agentsSolutions.size();i++)
            if(agentsSolutions.get(i).getPath().size()>solLength)
                solLength=agentsSolutions.get(i).getPath().size();
    }

    public int getSolLength() {
        return solLength;
    }

    public Solution(Object o) {
        if (!(o instanceof File))
            return;
        Scanner in = null;
        try {
            in = new Scanner(((File) o));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        while (in.hasNext()) {
            sb.append(in.next()+'\n');
        }
        in.close();
        agentsSolutions=new ArrayList<>();
        agentsSolutions=generateSolution(sb.toString());
    }


    public ArrayList generateSolution(String solStr) {
        String[] strArr = solStr.split("\n");
        for (int i = 0; i < strArr.length; i++) {
            AgentSolution agentSolution = makeSingleAgentSolution(strArr[i],i);
            addSolution(agentSolution);
        }
        return agentsSolutions;
    }


    private AgentSolution makeSingleAgentSolution(String s,int id) {
        ArrayList<Position> sol = new ArrayList();
        Position first=null;
        Position last = null;
        int p = 0, q = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                p = i;
                while (s.charAt(i) != ')') i++;
                    q = i;
                last = getPositionFromString(s.substring(p, q + 1));
                if(first==null)
                    first=last;
            } else continue;
            if (last != null) {
                sol.add(last);
            }
            else
                continue;
        }
        return new AgentSolution(new Agent(id,first,last),sol);
    }

    private Position getPositionFromString(String s) {
        if (s.length() < 3 || s.charAt(0) != '(' || s.charAt(s.length() - 1) != ')')
            return null;
        int i = 1;
        while (s.charAt(i) != ',') {
            i++;
        }
        String strX = s.substring(1, i);
        String strY = s.substring(++i, s.length() - 1);
        int x = Integer.parseInt(strX);
        int y = Integer.parseInt(strY);
        Position pos = new Position(x, y);
        return pos;
    }

    public void addSolution(AgentSolution sol) {
        agentsSolutions.add(sol);
        if(sol.getPath().size()>solLength)
            solLength=sol.getPath().size();
    }

    public void removeSolution(AgentSolution sol) {
        agentsSolutions.remove(sol);
    }
}
