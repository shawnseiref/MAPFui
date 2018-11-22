package backEnd.Error;

import backEnd.Agents.Agent;
import backEnd.MapGenerators.Position;

public class SkipMove extends AInvalidMove {
    public SkipMove(Agent agent, Position currentPos, Position invalidPos, int TimeStamp) {
        super(agent, currentPos, invalidPos, TimeStamp);
    }

    @Override
    public String getError() {
        String ans="Invalid move of type SkipMove: Agent "+agent.getId()+" traveled from "+currentPos.toString()+" to "+invalidPos.toString()+"by skipping illegally at "+timeStamp+" timestamp.";
        return ans;
    }
}
