package backEnd.Solution.SolutionValidators;

import backEnd.Agents.Agent;
import backEnd.Agents.AgentSolution;
import backEnd.Error.*;
import backEnd.Game.SubScenario;
import backEnd.MapGenerators.Map;
import backEnd.MapGenerators.Position;
import backEnd.Solution.Solution;
import javafx.geometry.Pos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class SimpleValidator extends ASolutionValidator {

//    public boolean isSuitableSolution(SubScenario scene){
//        Map map=scene.getMap();
//        Solution sol=scene.getSol();
//        List<Agent> agents=scene.getAgentsList();
//        ArrayList<AgentSolution> agentSolutions=sol.getAgentsSolutions();
//        if()
//        return true;
//    }

    public ArrayList<AError> validateSol(SubScenario scene) {
        ArrayList<AError> ans=new ArrayList<>();
        HashMap<Position,ArrayList<Agent>> currentPositions;//to checks conflict
        Map map=scene.getMap();
        Solution sol=scene.getSol();
        List<Agent> agents=scene.getAgentsList();
        ArrayList<AgentSolution> agentSolutions=sol.getAgentsSolutions();
        for (int j=1;j<sol.getSolLength();j++) {
            currentPositions = new HashMap<>();
            for (int i = 0; i < agents.size(); i++) {
                Position previousPos = agentSolutions.get(i).getPath().get(j-1);
                Position currentPos = agentSolutions.get(i).getPath().get(j);
                //check OutOfBoundsMove
                if (map.posExists(currentPos) == false) {
                    OutOfBoundsMove OOBMerror = new OutOfBoundsMove(agents.get(i), previousPos, currentPos, j);
                    ans.add(OOBMerror);
                }
                //check ObstacleMove
                else if (map.posReachable(currentPos)==false) {
                    ObstacleMove OMerror = new ObstacleMove(agents.get(i), previousPos, currentPos, j);
                    ans.add(OMerror);
                }
                //check SkipMove
                if (previousPos.isComplexNeighbor(currentPos) == false && previousPos.isSimpleNeighbor(currentPos) == false && previousPos.equals(currentPos)==false) {
                    SkipMove SMerror = new SkipMove(agents.get(i), previousPos, currentPos, j);
                    ans.add(SMerror);
                } else if (previousPos.isComplexNeighbor(currentPos) == true) {
                    int middleX = (previousPos.getX() + currentPos.getX()) / 2;
                    int middleY = (previousPos.getY() + currentPos.getY()) / 2;
                    Position middlePos = new Position(middleX, middleY);
                    if (map.posReachable(middlePos) == false) {
                        SkipMove SMerror = new SkipMove(agents.get(i), previousPos, currentPos, j);
                        ans.add(SMerror);
                    }
                }
                //prepare Hashmaps for conflicts
                ArrayList<Agent> PosAgentList;
                if(currentPositions.containsKey(currentPos))
                    PosAgentList=currentPositions.get(currentPos);
                else
                    PosAgentList=new ArrayList<>();
                PosAgentList.add(agents.get(i));
                currentPositions.put(currentPos,PosAgentList);
                //check EdgeConflict
                for (int t = i+1; t < agents.size(); t++) {
                    Position testedPreviousPos = agentSolutions.get(t).getPath().get(j-1);
                    Position testedCurrentPos = agentSolutions.get(t).getPath().get(j);
                    if (previousPos.equals(testedCurrentPos) && currentPos.equals(testedPreviousPos)) {
                        List<Agent> agentsListConfict = new ArrayList<>();
                        agentsListConfict.add(agents.get(i));
                        agentsListConfict.add(agents.get(t));
                        EdgeConflict ECerror = new EdgeConflict(agentsListConfict, previousPos, currentPos,j);
                        ans.add(ECerror);
                    }
                }
            }
            //check VertexConflict
            for (java.util.Map.Entry<Position, ArrayList<Agent>> entry : currentPositions.entrySet()) {
                List<Agent> agentsInPos = entry.getValue();
                if (agentsInPos.size() > 1) {
                    VertexConflict VCerror = new VertexConflict(agentsInPos, entry.getKey(), j);
                    ans.add(VCerror);
                }
            }
        }
        return ans;
    }
}
