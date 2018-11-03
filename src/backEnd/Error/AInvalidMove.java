package backEnd.Error;

import backEnd.Agents.Agent;
import backEnd.MapGenerators.Position;

public abstract class AInvalidMove implements IError {
    protected Agent agent;
    protected Position currentPos;
    protected Position invalidPos;

    public AInvalidMove(Agent agent, Position currentPos, Position invalidPos) {
        this.agent = agent;
        this.currentPos = currentPos;
        this.invalidPos = invalidPos;
    }
}
