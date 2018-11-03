package backEnd.Error;

import backEnd.Agents.Agent;
import backEnd.MapGenerators.Position;

public class EdgeConflict extends AConflict {

    public EdgeConflict(Agent first, Position firstCurrentPos, Agent second, Position secondCurrentPos) {
        super(first, firstCurrentPos, second, secondCurrentPos);
    }

    @Override
    public String getError() {
        return("Edge conflict:"+getDetailedMsg(secondCurrentPos,firstCurrentPos));
    }
}
