package frontend.view;

import backEnd.Agents.Agent;
import backEnd.Game.SubScenario;
import backEnd.MapGenerators.Map;
import backEnd.MapGenerators.Position;
import frontend.model.IModel;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;

public class SubScenDisplayer extends Canvas {

    private SubScenario game;
    private IModel.Type currentType= IModel.Type.CREATE;
    private int currentState=0;
    private double cellHeight;
    private double cellWidth;
    private double xLO;
    private double yLO;
    private double wLO;
    private double hLO;
    private double sxLO;
    private double syLO;
    private boolean start=false;

    public void setCurrentType(IModel.Type currentType) {
        this.currentType = currentType;
    }

    public IModel.Type getCurrentType() {
        return currentType;
    }

    public void setMap(Map map) {
        game = new SubScenario(map);
        if(!start){
            xLO=getLayoutX();
            yLO=getLayoutY();
            wLO=getWidth();
            hLO=getHeight();
            sxLO=getScaleX();
            syLO=getScaleY();
        }
        start=true;
        redraw();
    }
    public void nextState(){
        if(currentState<game.getSol().getSolLength()-1)
            currentState++;
    }

    public void previousState(){
        if(currentState!=0)
            currentState--;
    }

    public void redraw() {
        if(game==null)
            getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
        else if (game.getMap()!= null && game.getMap().getGrid()!=null) {
            char[][] grid=game.getMap().getGrid();
            double size=Math.max(20,Math.min(800/grid.length,800/grid[0].length));
            cellHeight = size;
            cellWidth = size;
            setHeight(cellHeight*grid.length);
            setWidth(cellWidth*grid[0].length);
            try {
                Image treeImage=null;
                Image characterImageWay = null;
                Image characterImage = null;
                Image outOfBounds=null;
                Image goal=null;
                Image visitedoutOfBounds=null;
                treeImage = new Image(this.getClass().getResourceAsStream("/Images/tree.png"));
                outOfBounds = new Image(this.getClass().getResourceAsStream("/Images/void.jpg"));
                GraphicsContext gc = getGraphicsContext2D();
                gc.clearRect(0, 0, getWidth(), getHeight());
                //Draw grid
                for (int i = 0; i < grid.length; i++) {
                    for (int j = 0; j < grid[i].length; j++) {
                        if (grid[i][j] == 'T') {
                            gc.drawImage(treeImage, j * cellWidth,i * cellHeight , cellWidth, cellHeight);
                        }
                        else if(grid[i][j]== '@'){
                            gc.drawImage(outOfBounds, j * cellWidth,i * cellHeight , cellWidth, cellHeight);
                        }
                    }
                }
                if(currentType== IModel.Type.SIMULATE && game.getSol()!=null) {
                    for (int t = 0; t < game.getSol().getAgentsSolutions().size(); t++) {
                        ArrayList<Position> path = game.getSol().getAgentsSolutions().get(t).getPath();
                        //characterImage=something;
                        int k = 0;
                        for (k = 0; k < currentState; k++) {
//                            gc.setFill(Color.color((double)1/((double)t+2), (double)1/((double)t+2), (double)1/((double)t+2), 0.5));
//                            gc.fillRect(path.get(k).getY() * cellWidth, path.get(k).getX() * cellHeight, cellWidth, cellHeight);
                        }
                        gc.setFill(Color.color((double)1/((double)t+2),(double)1/((double)t+2),(double)1/((double)t+2)));
                        gc.fillRect(path.get(k).getY() * cellWidth, path.get(k).getX() * cellHeight, cellWidth, cellHeight);
                    }
                }
                else if(currentType== IModel.Type.CREATE && game.getAgentsList()!=null){
                    ArrayList<Agent> agents=game.getAgentsList();
                    for(int t=0;t<agents.size();t++){
                        gc.setFill(Color.color((double)1/((double)t+2),(double)1/((double)t+2),(double)1/((double)t+2)));
                        gc.fillRect( agents.get(t).getLocation().getY() * cellWidth,agents.get(t).getLocation().getX() * cellHeight , cellWidth, cellHeight);
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void setGame(SubScenario game) {
        this.game=game;
    }
}
