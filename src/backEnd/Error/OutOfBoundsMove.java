package backEnd.Error;

import backEnd.Agents.Agent;
import backEnd.MapGenerators.Position;

public class OutOfBoundsMove extends AInvalidMove {
    public OutOfBoundsMove(Agent agent, Position currentPos, Position invalidPos, int TimeStamp) {
        super(agent, currentPos, invalidPos, TimeStamp);
    }

    @Override
    public String getError() {
        String ans="Invalid move of type OutOfBoundsMove: Agent "+agent.getId()+" traveled from "+currentPos.toString()+" into an out of bounds position in "+invalidPos.toString()+" at "+timeStamp+" timestamp.";
        return ans;
    }
}
