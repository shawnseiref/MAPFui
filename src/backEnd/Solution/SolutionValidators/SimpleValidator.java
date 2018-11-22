package backEnd.Solution.SolutionValidators;

import backEnd.Agents.Agent;
import backEnd.Error.AError;
import backEnd.Game.SubScenario;
import backEnd.MapGenerators.Map;
import backEnd.MapGenerators.Position;
import backEnd.Solution.Solution;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class SimpleValidator extends ASolutionValidator {
    @Override
    public ArrayList<AError> validateSol(SubScenario scene) {
        ArrayList<AError> ans=new ArrayList<>();
        HashMap<Position,ArrayList<Agent>> positions=new HashMap<>();//to checks conflict
        Map map=scene.getMap();
        Solution sol=scene.getSol();
        Position[][] array=new Position[scene.getAgentsList().size()][sol.getSolLength()];
        for (int j=0;j+1<array[0].length;j++){
            for (int i=0;i<array.length;i++){
                // TODO: 22-Nov-18 // check and find Errors; 
            }
        }
        return ans;
    }
}
