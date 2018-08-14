package backEnd.Game;

import java.util.ArrayList;

public class Scenario {
    private ArrayList scenarios;

    public Scenario() {
        scenarios=new ArrayList();
    }

    public Scenario(ArrayList scenarios) {
        this.scenarios = scenarios;
    }

    public ArrayList getScenarios() {
        return scenarios;
    }

    public void setScenarios(ArrayList scenarios) {
        this.scenarios = scenarios;
    }

    public void addScenario(Scenario sc){
        scenarios.add(sc);
    }

    public void removeScenario(Scenario sc){
        scenarios.remove(sc);
    }
}
