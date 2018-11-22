package backEnd.Error;

import backEnd.Agents.Agent;
import backEnd.MapGenerators.Position;

import java.util.List;

public class VertexConflict extends AConflict {
    protected Position conflictedPos;

    public VertexConflict(List<Agent> Agents,Position conflictedPos,int TimeStamp) {
        super(Agents,TimeStamp);
        this.conflictedPos = conflictedPos;
    }

    @Override
    public String getError() {
        String ans="Vertex Conflict: ";
        ans=ans+getAgentsIDs();
        ans=ans+" go to "+conflictedPos.toString()+" at "+timeStamp+" timeStamp";
        return ans;
    }
}
