package backEnd.Error;

import backEnd.Agents.Agent;
import backEnd.MapGenerators.Position;
import javafx.geometry.Pos;

import java.util.List;

public abstract class AConflict extends AError{
    protected List<Agent> agents;

    public AConflict(List<Agent> Agents,int TimeStamp) {
        super(TimeStamp);
        this.agents=Agents;
    }

    protected String getAgentsIDs() {
        String ans = "Agents ";
        for (Agent agent : agents) {
            ans += agent.getId() + ",";
        }
        if(ans.length()>0)
            ans=ans.substring(0,ans.length()-1);
        return ans;
    }
}
