package backEnd.Error;

import backEnd.Agents.Agent;
import backEnd.MapGenerators.Position;
import javafx.geometry.Pos;

import java.util.List;

public class EdgeConflict extends AConflict {
    Position firstConflictPos;
    Position secondConflictPos;

    public EdgeConflict(List<Agent> Agents, Position FirstConflictPos,Position SecondConflictPos, int TimeStamp) {
        super(Agents,TimeStamp);
        this.firstConflictPos = FirstConflictPos;
        this.secondConflictPos = SecondConflictPos;
    }

    @Override
    public String getError() {
        String ans="Vertex Conflict: ";
        ans=ans+getAgentsIDs();
        ans=ans+" go from "+firstConflictPos.toString()+" to "+secondConflictPos.toString()+" (or the opposite) at "+timeStamp+" timeStamp";
        return ans;
    }
}
