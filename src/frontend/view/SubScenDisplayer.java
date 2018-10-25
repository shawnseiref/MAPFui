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

public class SubScenDisplayer extends Canvas {

    private SubScenario game;
    private IModel.Type currentType = IModel.Type.CREATE;
    private int currentState = 0;
    private double cellSize;
    private double agentWidth;
    private boolean start = false;

    public void setCurrentType(IModel.Type currentType) {
        this.currentType = currentType;
    }

    public IModel.Type getCurrentType() {
        return currentType;
    }

    public void setMap(Map map) {
        game = new SubScenario(map);
        start = true;
        redraw();
    }

    public void currentStateZero(){
        currentState=0;
    }

    public boolean nextState() {
        if (currentState < game.getSol().getSolLength() - 1){
            currentState++;
            return true;
        }
        return false;
    }

    public boolean previousState() {
        if (currentState != 0){
            currentState--;
            return true;
        }
        return false;
    }

    public void redraw() {
        if (game == null)
            getGraphicsContext2D().clearRect(0, 0, getWidth(), getHeight());
        else if (game.getMap() != null && game.getMap().getGrid() != null) {
            char[][] grid = game.getMap().getGrid();
            setHeight(cellSize * grid[0].length);
            setWidth(cellSize * grid.length);
            try {
//                Image treeImage = null;
//                Image outOfBounds = null;
//                treeImage = new Image(this.getClass().getResourceAsStream("/Images/tree.png"));
//                outOfBounds = new Image(this.getClass().getResourceAsStream("/Images/void.jpg"));
                GraphicsContext gc = getGraphicsContext2D();
                gc.clearRect(0, 0, getWidth(), getHeight());
                //Draw grid
                for (int x = 0; x < grid.length; x++) {
                    for (int y = 0; y < grid[x].length; y++) {
                        if (grid[x][y] == 'T') {
//                            gc.drawImage(treeImage, x * cellSize, y * cellSize, cellSize, cellSize);
                            gc.setFill(Color.gray(0.25));
                            gc.fillRect( x * cellSize, y * cellSize, cellSize, cellSize);
                        } else if (grid[x][y] == '@') {
//                            gc.drawImage(outOfBounds, x * cellSize, y * cellSize, cellSize, cellSize);
                            gc.setFill(Color.BLACK);
                            gc.fillRect( x * cellSize, y * cellSize, cellSize, cellSize);
                        }
                    }
                }
                if (currentType == IModel.Type.SIMULATE && game.getSol() != null) {
                    drawPath(gc);
                }
                if (game.getAgentsList() != null) {
                    drawAgents(gc);
                    drawTargets(gc);
                }
                if (cellSize != 0)
                    drawGrid(gc);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void drawPath(GraphicsContext gc) {
        agentWidth = cellSize / (game.getSol().getAgentsSolutions().size());
        double empty=0.2;
        for (int i = 0; i < game.getSol().getAgentsSolutions().size(); i++) {
            ArrayList<Position> path = game.getSol().getAgentsSolutions().get(i).getPath();
            int j = 0;
            Color color = getColor(i);
            for (j = 0; j < currentState; j++) {
                gc.setFill(color);
                if(path.get(j).getX()<path.get(j+1).getX()){//move to right
                    gc.fillRect(path.get(j).getX() * cellSize + i * agentWidth+empty*agentWidth ,path.get(j).getY() * cellSize + i*agentWidth+empty*agentWidth, cellSize+agentWidth*(1-2*empty), agentWidth*(1-2*empty));
                }
                if(path.get(j).getX()>path.get(j+1).getX()){//move to left
                    gc.fillRect(path.get(j+1).getX() * cellSize + i * agentWidth+empty*agentWidth ,path.get(j+1).getY() * cellSize + i*agentWidth+empty*agentWidth, cellSize+agentWidth*(1-2*empty), agentWidth*(1-2*empty));
                }
                if(path.get(j).getY()<path.get(j+1).getY()){//move to down
                    gc.fillRect(path.get(j).getX() * cellSize + i * agentWidth+empty*agentWidth, path.get(j).getY() * cellSize + i*agentWidth+empty*agentWidth, agentWidth*(1-2*empty), cellSize+agentWidth*(1-2*empty));
                }
                if(path.get(j).getY()>path.get(j+1).getY()){//move to up
                    gc.fillRect(path.get(j+1).getX() * cellSize + i * agentWidth+empty*agentWidth, path.get(j+1).getY() * cellSize + i*agentWidth+empty*agentWidth, agentWidth*(1-2*empty), cellSize+agentWidth*(1-2*empty));
                }
            }
        }
    }

    private void drawTargets(GraphicsContext gc) {
        ArrayList<Agent> agents = game.getAgentsList();
        boolean posTaken=false;
        for (int i = 0; i < agents.size(); i++) {
            Agent agent=agents.get(i);
            for (int j = 0; j < agents.size(); j++) {
                if(agent.getGoalLocation().equals(agents.get(j).getLocation())==true)
                    posTaken=true;
            }
            if(posTaken==false){
                drawAgent(agent.getGoalLocation().getX(), agent.getGoalLocation().getY(), i, true, gc);
                drawTarget(agent.getGoalLocation().getX(), agent.getGoalLocation().getY(), i, gc);
            }
            posTaken=false;
        }
    }

    private void drawTarget(int x, int y, int botNum, GraphicsContext gc) {
        gc.setFill(Color.WHITE);
        gc.fillOval(x * cellSize+0.1*cellSize,
                y * cellSize+0.1*cellSize,
                cellSize*0.8, cellSize*0.8);
        int stringSize = Math.max(10, (int) cellSize / 5);
        gc.setFont(new Font(stringSize));
        gc.setFill(Color.BLACK);
        gc.fillText(botNum + "", x * cellSize + cellSize / 2 - stringSize * 0.45 * ((int) Math.log10(botNum + 1) + 1), y * cellSize + cellSize / 2 + stringSize * 0.45, cellSize);
    }

    private void drawAgents(GraphicsContext gc) {
        ArrayList<Agent> agents = game.getAgentsList();
        for (int t = 0; t < agents.size(); t++) {
            drawAgent(agents.get(t).getLocation().getX(), agents.get(t).getLocation().getY(), t, true, gc);
        }
    }

    private void drawAgent(int x, int y, int botNum, boolean drawNum, GraphicsContext gc) {

        gc.setFill(drawNum? getColor(botNum): Color.RED);
        gc.fillOval(x * cellSize, y * cellSize, cellSize, cellSize);
        if (drawNum) {
            int stringSize = Math.max(10, (int) cellSize / 5);
            gc.setFont(new Font(stringSize));
            gc.setFill(Color.WHITE);
            gc.fillText(botNum + "", x * cellSize + cellSize / 2 - stringSize * 0.45 * ((int) Math.log10(botNum + 1) + 1), y * cellSize + cellSize / 2 + stringSize * 0.45, cellSize);
        }
    }

    public void drawTempAgent(int x, int y) {
        drawAgent(x, y, 0, false, getGraphicsContext2D());
    }



    private void drawGrid(GraphicsContext gc) {
        gc.setLineWidth(1); // change the line width
        char[][] grid = game.getMap().getGrid();

        int hLineCount = (int) Math.floor(grid[0].length+1);
        int vLineCount = (int) Math.floor(grid.length+1);

        gc.setStroke(Color.BLACK);
        double one=grid.length*cellSize;
        double two=getWidth();
        for (int i = 0; i < hLineCount; i++) {
            gc.strokeLine(0, snap((i + 1) * cellSize), getWidth(), snap((i + 1) * cellSize));
        }

        for (int i = 0; i < vLineCount; i++) {
            gc.strokeLine(snap((i + 1) * cellSize), 0, snap((i + 1) * cellSize), getHeight());
        }
    }

    private double snap(double y) {
        return ((int) y) + 0.5;
    }


    private Color getColor(int t) {
        int ID = t + 1;
        int r = (ID & 4) >> 2,
                g = (ID & 2) >> 1,
                b = ID & 1,
                h = (ID & 8) >> 3;
        return (Color.rgb(100 * r + h * 80, 140 * g + h * 80, 100 * b + h * 80));
    }


    public void setGame(SubScenario game) {
        this.game = game;
    }

    public int getStateNum() {
        return currentState;
    }

    public void changeSize(double zoomFactor) {
        char[][] grid = game.getMap().getGrid();
        if (cellSize * zoomFactor * grid.length < 8192 && cellSize * zoomFactor * grid[0].length < 8192) {
            cellSize = cellSize * zoomFactor;
            redraw();
        }
    }

    public double getSize() {
        if (game == null)
            return 0;
        return cellSize;
    }

    public void newMap() {
        double size = computeSize();
        cellSize = size;
        redraw();
    }


    private double computeSize() {
        char[][] grid = game.getMap().getGrid();
        double size = Math.max(100, Math.min(getWidth() / grid.length,getHeight() / grid[0].length));
//        double size = Math.max(100, Math.min(getParent().getParent().getBoundsInParent().getWidth() / grid.length, getParent().getParent().getBoundsInParent().getHeight() / grid[0].length));
        size = Math.min(8192 / Math.max(grid.length, grid[0].length), size);
        return size;
    }
}
