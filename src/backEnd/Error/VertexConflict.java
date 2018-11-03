package backEnd.Error;

import backEnd.Agents.Agent;
import backEnd.MapGenerators.Position;

public class VertexConflict extends AConflict {
    protected Position conflictedPos;

    public VertexConflict(Agent first, Position firstCurrentPos, Agent second, Position secondCurrentPos, Position conflictedPos) {
        super(first, firstCurrentPos, second, secondCurrentPos);
        this.conflictedPos = conflictedPos;
    }

    @Override
    public String getError() {
        return("Vertex conflict:"+getDetailedMsg(conflictedPos,conflictedPos));
    }
}
