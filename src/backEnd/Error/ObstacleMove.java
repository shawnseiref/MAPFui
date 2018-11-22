package backEnd.Error;

import backEnd.Agents.Agent;
import backEnd.MapGenerators.Position;

public class ObstacleMove extends AInvalidMove {
    public ObstacleMove(Agent agent, Position currentPos, Position invalidPos, int TimeStamp) {
        super(agent, currentPos, invalidPos, TimeStamp);
    }

    @Override
    public String getError() {
        String ans="Invalid move of type ObstacleMove: Agent "+agent.getId()+" traveled from "+currentPos.toString()+" into an obstacle in "+invalidPos.toString()+" at "+timeStamp+" timestamp.";
        return ans;
    }
}
