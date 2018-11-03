package backEnd.Error;

import backEnd.Agents.Agent;
import backEnd.MapGenerators.Position;
import javafx.geometry.Pos;

public abstract class AConflict implements IError{
    protected Agent first;
    protected Position firstCurrentPos;
    protected Agent second;
    protected Position secondCurrentPos;

    public AConflict(Agent first, Position firstCurrentPos, Agent second, Position secondCurrentPos) {
        this.first = first;
        this.firstCurrentPos = firstCurrentPos;
        this.second = second;
        this.secondCurrentPos = secondCurrentPos;
    }

    protected String getDetailedMsg(Position firstConflictPos,Position secondConflictPos){
        return ("{Agent number "+first.getId()+" from "+firstCurrentPos.toString()+" to "+firstConflictPos.toString()+" <> "+"Agent number "+second.getId()+" from "+secondCurrentPos.toString()+" to "+secondConflictPos.toString()+"}");
    }
}
