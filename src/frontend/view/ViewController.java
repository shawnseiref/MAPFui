package frontend.view;

import backEnd.MapGenerators.Map;
import backEnd.MapGenerators.Position;
import frontend.model.IModel;
import frontend.viewmodel.ViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.transform.Scale;
import javafx.stage.FileChooser;

import javax.swing.*;
import java.io.File;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class ViewController implements Observer,IView, Initializable {

    @FXML
    public BorderPane root;
    @FXML
    private ViewModel viewModel;
    @FXML
    public SubScenDisplayer subSceneDisplayer;
    public javafx.scene.control.ScrollPane scroll;
    public javafx.scene.control.MenuBar menu;
    public javafx.scene.layout.VBox controlVbox;
    public javafx.scene.layout.VBox createVbox;
    public javafx.scene.control.TabPane tab;
    public javafx.scene.control.TextField startRow;
    public javafx.scene.control.TextField startCol;
    public javafx.scene.control.TextField goalRow;
    public javafx.scene.control.TextField goalCol;
    public javafx.scene.control.TextField fileName;
    public javafx.scene.control.Button  solBut;
    public javafx.scene.image.ImageView backward;
    public javafx.scene.image.ImageView pause;
    public javafx.scene.image.ImageView play;
    public javafx.scene.image.ImageView forward;


    @Override
    public void update(Observable o, Object arg) {
        if (o == viewModel) {
            redraw();
            //displayGame(viewModel.getMap().getGrid());
//            btn_generateMaze.setDisable(false);
        }
    }

//    @Override
    public void displayGame(char[][] map) {
        //subScenDisplayer.setMaze(map);
//        int characterPositionRow = viewModel.getCharacterPositionRow();
//        int characterPositionColumn = viewModel.getCharacterPositionColumn();
//        subScenDisplayer.setCharacterPosition(characterPositionRow, characterPositionColumn);
//        this.characterPositionRow.set(characterPositionRow + "");
//        this.characterPositionColumn.set(characterPositionColumn + "");
    }

    public ViewModel getViewModel() {
        return viewModel;
    }

    public void setViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void addAgent(ActionEvent actionEvent){
        try{
            Position start=new Position(Integer.parseInt(startRow.getText()),Integer.parseInt(startCol.getText()));
            Position goal=new Position(Integer.parseInt(goalRow.getText()),Integer.parseInt(goalCol.getText()));
            Map map=viewModel.getMap(IModel.Type.CREATE);
            if(!map.posExists(start) || !map.posExists(goal) || !map.posReachable(start) || !map.posReachable(goal))
                throw new Exception();
            viewModel.addAgent(start,goal, IModel.Type.CREATE);
        }
        catch (Exception e){
            showAlert("Agent's position must be a reachable location in the map");
        }
    }

    public void loadSol(ActionEvent event){
        File file=loadSolFile("");
        if(file!=null){
            viewModel.loadSol(file);
            controlVbox.setVisible(true);
        }
        event.consume();
    }

    public void forwardFunc(MouseEvent event){
        subSceneDisplayer.nextState();
        redraw();
        event.consume();
    }

    public void backwardsFunc(MouseEvent event){
        subSceneDisplayer.previousState();
        redraw();
        event.consume();
    }

    public void loadMap(ActionEvent event){
        String path="";
        IModel.Type current=subSceneDisplayer.getCurrentType();
        if(current == IModel.Type.SIMULATE)
            path="Created Files";
        else
            path="SavedMaps";
        File file=loadMapFile(path);
        if (file != null) {
            viewModel.loadMap(file, current);
        }
        redraw();
        if(subSceneDisplayer.getCurrentType()== IModel.Type.CREATE && viewModel.getGame(IModel.Type.CREATE)!=null)
            createVbox.setVisible(true);
        else if (subSceneDisplayer.getCurrentType()== IModel.Type.SIMULATE && viewModel.getGame(IModel.Type.SIMULATE)!=null)
            solBut.setVisible(true);
        event.consume();
    }

    private File loadSolFile(String location) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Solution files", "*.sol"));
        fc.setTitle("Load Solution");
        //showing the file chooser
        return fc.showOpenDialog(null);
    }

    private File loadMapFile(String location) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Load Map");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("map files", "*.map"));
        if (location.equals("SavedMaps"))
            fc.setInitialDirectory(new File("SavedMaps"));
        else if (location.equals("Created Files"))
            fc.setInitialDirectory(new File("Created Files"));
        //showing the file chooser
        return fc.showOpenDialog(null);
    }

    private void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createVbox.setVisible(false);
        controlVbox.setVisible(false);
        solBut.setVisible(false);
        backward.setImage(new Image(this.getClass().getResourceAsStream("/Images/backwards.png")));
        pause.setImage(new Image(this.getClass().getResourceAsStream("/Images/pause.png")));
        play.setImage(new Image(this.getClass().getResourceAsStream("/Images/play.png")));
        forward.setImage(new Image(this.getClass().getResourceAsStream("/Images/forwards.png")));
    }

    public void simulateSelect(Event event){
        if(subSceneDisplayer!=null) {
            subSceneDisplayer.setCurrentType(IModel.Type.SIMULATE);
            redraw();
        }
        event.consume();
    }

    public void createSelect(Event event){
        if(subSceneDisplayer!=null) {
            subSceneDisplayer.setCurrentType(IModel.Type.CREATE);
            redraw();
        }
        event.consume();
    }

    public void save(ActionEvent event) throws Exception {
        try{
            viewModel.createFiles(fileName.getText());
        }
        catch (Exception e){
            showAlert("Please choose a different name");
        }
        event.consume();
    }

    public void redraw(){
        if(subSceneDisplayer!=null){
            subSceneDisplayer.setGame(viewModel.getGame(subSceneDisplayer.getCurrentType()));
            subSceneDisplayer.redraw();
        }
    }

    public void setResizeEvent(Scene scene) {
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                Scale newScale = new Scale();
//                if(newSceneWidth.doubleValue()>tab.widthProperty().doubleValue())
//                    scroll.setPrefWidth(newSceneWidth.doubleValue()-tab.widthProperty().doubleValue());
//                double old=oldSceneWidth.doubleValue()-VB.getWidth();
//                double neww=newSceneWidth.doubleValue()-VB.getWidth();
                if(subSceneDisplayer!=null){
//                    subSceneDisplayer.setWidth(subSceneDisplayer.getWidth()+newSceneWidth.doubleValue()-oldSceneWidth.doubleValue());
//                    newScale.setPivotX(subSceneDisplayer.getLayoutX() *(neww)/(old));
//                    newScale.setX( subSceneDisplayer.getScaleX() * (neww)/(old) );
//                    subSceneDisplayer.getTransforms().add(newScale);
                }
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
//                if(newSceneHeight.doubleValue()>tab.heightProperty().doubleValue())
//                    scroll.setPrefHeight(newSceneHeight.doubleValue()-tab.heightProperty().doubleValue());
                Scale newScale = new Scale();
                double old=oldSceneHeight.doubleValue()-menu.getHeight();
                double neww=newSceneHeight.doubleValue()-menu.getHeight();
                if(subSceneDisplayer!=null){
//                    subSceneDisplayer.setHeight(subSceneDisplayer.getHeight()+newSceneHeight.doubleValue()-oldSceneHeight.doubleValue());
//                    newScale.setPivotY(subSceneDisplayer.getLayoutY() *(neww)/(old));
//                    newScale.setY( subSceneDisplayer.getScaleY() * (neww)/(old) );
//                    subSceneDisplayer.getTransforms().add(newScale);
                }
            }
        });
    }

    public void KeyPressed(KeyEvent keyEvent) {
        try {
            viewModel.getMap(subSceneDisplayer.getCurrentType());
//            if (!mazeDisplayer.getFinished())
//                viewModel.moveCharacter(keyEvent.getCode());
            keyEvent.consume();
//            btn_solveMaze.setDisable(false);
        } catch (NullPointerException e) {
            keyEvent.consume();
        }
    }

    public void zoomInOut(ScrollEvent scrollEvent) {
        try {
//            btn_zoomBack.setDisable(false);
//            btn_zoomBack.setVisible(true);
//            btn_zoomBack.setEffect(new Glow(1));
            viewModel.getMap(subSceneDisplayer.getCurrentType());
            AnimatedZoomOperation zoomOperator = new AnimatedZoomOperation();
            double zoomFactor;
            if (scrollEvent.isControlDown()) {
                zoomFactor = 1.5;
                double deltaY = scrollEvent.getDeltaY();
                if (deltaY < 0) {
                    zoomFactor = 1 / zoomFactor;
                }
                zoomOperator.zoom(scroll, zoomFactor, scrollEvent.getSceneX(), scrollEvent.getSceneY());
                scrollEvent.consume();
            }
        } catch (NullPointerException e) {
            scrollEvent.consume();
        }
    }


//    public void zoomBack(ActionEvent actionEvent) {
//        btn_zoomBack.setDisable(true);
//        Timeline timeline = new Timeline(60);
//        timeline.getKeyFrames().clear();
//        timeline.getKeyFrames().addAll(
//                new KeyFrame(Duration.millis(100), new KeyValue(scrollPane.translateXProperty(), 0)),
//                new KeyFrame(Duration.millis(100), new KeyValue(scrollPane.translateYProperty(), 0)),
//                new KeyFrame(Duration.millis(100), new KeyValue(scrollPane.scaleXProperty(), 1)),
//                new KeyFrame(Duration.millis(100), new KeyValue(scrollPane.scaleYProperty(), 1)));
//        timeline.play();
//        btn_zoomBack.setVisible(false);
//    }
}
