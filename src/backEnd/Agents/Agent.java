package backEnd.Agents;

import backEnd.MapGenerators.Position;

public class Agent {
    private int id;
    private Position location;
    private Position goalLocation;

    public Agent(int id) {
        this.id = id;
        location=null;
        goalLocation=null;
    }

    public Agent(int id, Position location, Position goalLocation) {
        this.id = id;
        this.location = location;
        this.goalLocation = goalLocation;
        path=new AgentSolution();
        path.addPosition(location);
    }

    private AgentSolution path;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Position getLocation() {
        return location;
    }

    public void setLocation(Position location) {
        this.location = location;
    }

    public Position getGoalLocation() {
        return goalLocation;
    }

    public void setGoalLocation(Position goalLocation) {
        this.goalLocation = goalLocation;
    }

    public AgentSolution getPath() {
        return path;
    }

    public void setPath(AgentSolution path) {
        this.path = path;
    }

    public double getRealDistance(){
        double ans=0;
        double deltaX=Math.abs(goalLocation.getX()-location.getX());
        double deltaY=Math.abs(goalLocation.getY()-location.getY());
        ans+=Math.min(deltaX,deltaY)*Math.sqrt(2);
        ans+=Math.max(deltaX,deltaY)-Math.min(deltaX,deltaY);
        ans=Math.round(ans*Math.pow(10,8))/Math.pow(10,8);//fix to 8 digits after decimal
        return ans;
    }

}
