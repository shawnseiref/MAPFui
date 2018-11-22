package backEnd.Error;

import backEnd.Agents.Agent;
import backEnd.MapGenerators.Position;

public abstract class AInvalidMove extends AError {
    protected Agent agent;
    protected Position currentPos;
    protected Position invalidPos;

    public AInvalidMove(Agent agent, Position currentPos, Position invalidPos,int TimeStamp) {
        super(TimeStamp);
        this.agent = agent;
        this.currentPos = currentPos;
        this.invalidPos = invalidPos;
    }
}
