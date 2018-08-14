package backEnd.Solvers;

import backEnd.Agents.AgentSolution;
import backEnd.MapGenerators.Map;
import backEnd.MapGenerators.Position;
import backEnd.MapGenerators.StringMapGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URI;
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

//    public static void main(String[] args) {
//        Solution sol = new Solution();
//        sol.agentsSolutions = sol.generateSolution("0.(1,1)(1,2)(1,3)\n" +
//                "1.(2,2)(2,3)(2,4)\n" +
//                "2.(3,3)(3,2)(3,1)");
//        sol.printSol();
//    }
//
//    private void printSol() {
//        for (int i = 0; i < agentsSolutions.size(); i++) {
//            AgentSolution a = (AgentSolution) agentsSolutions.get(i);
//            a.printPath();
//            System.out.println();
//        }
//
//    }


    public ArrayList generate(Object o) {
        if (!(o instanceof File))
            return null;
        Scanner in = null;
        try {
            in = new Scanner(((File) o));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        while (in.hasNext()) {
            sb.append(in.next());
        }
        in.close();
        return generateSolution(sb.toString());
    }

    public ArrayList generateSolution(String solStr) {
        String[] strArr = solStr.split("\n");
        for (int i = 0; i < strArr.length; i++) {
            AgentSolution agentSolution = makeSingleAgentSoltion(strArr[i]);
            addSolution(agentSolution);
        }
        return agentsSolutions;
    }


    private AgentSolution makeSingleAgentSoltion(String s) {
        AgentSolution agentSolution = new AgentSolution();
        ArrayList sol = new ArrayList();
        Position position = null;
        int p = 0, q = 0;
        boolean isPos = false;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(') {
                p = i;
                isPos = true;
                while (s.charAt(i) != ')') i++;
                    q = i;
                    position = getPositionFromString(s.substring(p, q + 1));
            } else continue;
            if (position != null) {
                agentSolution.addPosition(position);
            } else continue;
        }
        return agentSolution;
    }

    private Position getPositionFromString(String s) {
        if (s.length() < 3 || s.charAt(0) != '(' || s.charAt(s.length() - 1) != ')') return null;
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
