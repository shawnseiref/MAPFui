package frontend.view;

import backEnd.Agents.Agent;
import backEnd.Game.SubScenario;
import backEnd.MapGenerators.Map;
import backEnd.MapGenerators.Position;
import frontend.model.IModel;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Pair;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

public class SubScenDisplayer extends Canvas{

    private SubScenario game;
    private IModel.Type currentType= IModel.Type.CREATE;
    private int currentState=0;
    private double cellHeight=0;
    private double cellWidth=0;
    private double agentWidth;
    private boolean start=false;

    public void setCurrentType(IModel.Type currentType) {
        this.currentType = currentType;
    }

    public IModel.Type getCurrentType() {
        return currentType;
    }

    public void setMap(Map map) {
        game = new SubScenario(map);
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
            double size=Math.max(100,Math.min(800/grid.length,800/grid[0].length));
            size=Math.min(8192/Math.max(grid.length,grid[0].length),size);
            if(cellHeight==0 || cellWidth==0){
                cellHeight = size;
                cellWidth = size;
            }
            setHeight(cellHeight*grid.length);
            setWidth(cellWidth*grid[0].length);
            try {
                Image treeImage=null;
                Image outOfBounds=null;
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
                    agentWidth=cellWidth/game.getSol().getAgentsSolutions().size();
                    for (int t = 0; t < game.getSol().getAgentsSolutions().size(); t++) {
                        ArrayList<Position> path = game.getSol().getAgentsSolutions().get(t).getPath();
                        int k = 0;
                        Color color=getColor(t);
                        for (k = 0; k < currentState; k++) {
                            gc.setFill(color);
                            gc.fillRect(path.get(k).getY() * cellWidth+t*agentWidth, path.get(k).getX() * cellHeight, agentWidth, cellHeight);
                        }
                    }
                }
                if(game.getAgentsList()!=null){
                    ArrayList<Agent> agents=game.getAgentsList();
                    for(int t=0;t<agents.size();t++){
                        gc.setFill(getColor(t));
                        gc.fillOval(agents.get(t).getLocation().getY() * cellWidth, agents.get(t).getLocation().getX() * cellHeight, cellWidth, cellHeight);
                        int stringSize=Math.max(10,(int)size/5);
                        gc.setFont(new Font(stringSize));
                        gc.setFill(Color.WHITE);
                        gc.fillText(t+"",agents.get(t).getLocation().getY() * cellWidth+cellWidth/2-stringSize*0.45*((int)Math.log10(t+1)+1),agents.get(t).getLocation().getX() * cellHeight+cellHeight/2+stringSize*0.45,cellWidth);
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private Color getColor(int t){
        int ID=t+1;
        int r = (ID & 4) >> 2,
                g = (ID & 2) >> 1,
                b = ID & 1,
                h = (ID & 8) >> 3;
        return (Color.rgb(100 * r + h * 80, 140 * g + h * 80, 100 * b + h * 80));
    }


    public void setGame(SubScenario game) {
        this.game=game;
    }

    public int getStateNum() {
        return currentState;
    }

    public void changeSize(double zoomFactor) {
        char[][] grid=game.getMap().getGrid();
        if(cellWidth*zoomFactor*grid.length<8192 && cellHeight*zoomFactor*grid[0].length<8192){
            cellWidth=cellWidth*zoomFactor;
            cellHeight=cellHeight*zoomFactor;
            redraw();
        }
    }

}
