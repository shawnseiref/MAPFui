package frontend.view;

import javafx.fxml.FXML;
import backEnd.MapGenerators.Map;
import backEnd.MapGenerators.Position;
import frontend.model.IModel;
import frontend.viewmodel.ViewModel;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.*;

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
    public javafx.scene.control.Label xIndex;
    public javafx.scene.control.Label yIndex;
    public javafx.scene.control.Slider playSpeed;

    private boolean clicked=false;
    private int x;
    private int y;
    private boolean playButton =false;
    private Thread thread;


    @Override
    public void update(Observable o, Object arg) {
        if (o == viewModel) {
            redraw();
        }
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
        startRow.clear();
        startCol.clear();
        goalCol.clear();
        goalRow.clear();
    }

    public void loadSol(ActionEvent event){
        File file=loadSolFile("");
        if(file!=null){
            subSceneDisplayer.currentStateZero();
            if(viewModel.loadSol(file)==false){
                if(showConfirmation("There are errors in the solution\nDo you want to save the errors to log file?")){
                    FileChooser fc=new FileChooser();
                    fc.setTitle("Choose location To Save Report");
                    fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("TXT files",".txt"));
                    File fileToSaveTo=fc.showSaveDialog(subSceneDisplayer.getScene().getWindow());
                    viewModel.writeErrors(fileToSaveTo);
                }
            }
            else{
                showAlert("No errors were found in the solution!");
            }
            controlVbox.setVisible(true);
        }
        event.consume();
    }

    public void randomMap(ActionEvent event){
        try {
            Stage stage = new Stage();
            stage.setTitle("Random Map");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("randomMap.fxml").openStream());
            Scene scene = new Scene(root, 300, 300);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL); //Lock the window until it closes
            randomMapController controller=fxmlLoader.getController();
            controller.setViewController(this);
            stage.show();
        }
        catch (Exception e) {e.printStackTrace(); }
    }

    public void forwardFunc(MouseEvent event){
        viewModel.moveState(subSceneDisplayer.getStateNum(),subSceneDisplayer.getStateNum()+1);
        if(subSceneDisplayer.nextState()==false)
            playButton =false;
        redraw();
        event.consume();
    }

    public void backwardsFunc(MouseEvent event){
        viewModel.moveState(subSceneDisplayer.getStateNum(),subSceneDisplayer.getStateNum()-1);
        subSceneDisplayer.previousState();
        redraw();
        event.consume();
    }

    public void play(MouseEvent event){
        playButton =true;
        thread=new Thread(() -> { playLoop(event); });
        thread.start();
    }

    private void playLoop(MouseEvent event) {
        while(playButton ==true){
            try {
                Thread.sleep(((long)(105-playSpeed.getValue())*20));
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
            forwardFunc(event);
        }
        pause(event);
        //thread.stop();
    }

    public void pause(MouseEvent event){
        playButton =false;
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
            newMap();
        }
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
        File file=checkIfExists(location);
        fc.setInitialDirectory(file);
        //showing the file chooser
        return fc.showOpenDialog(null);
    }

    private File checkIfExists(String location) {
        File file=new File(System.getProperty("user.dir")+"/"+location);
        if(file.exists()==false)
            file.mkdir();
        return file;
    }


    public void showAlert(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(alertMessage);
        alert.show();
    }

    public boolean showConfirmation(String alertMessage) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(alertMessage);
        Optional<ButtonType> result = alert.showAndWait();
        return (result.get()==ButtonType.OK);
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
            disableLabels();
        }
        event.consume();
    }

    public void createSelect(Event event){
        if(subSceneDisplayer!=null) {
            subSceneDisplayer.setCurrentType(IModel.Type.CREATE);
            redraw();
            disableLabels();
        }
        event.consume();
    }

    public void save(ActionEvent event) throws Exception {
        try{
            checkIfExists("Created Files");
            viewModel.createFiles(fileName.getText());
        }
        catch (Exception e){
            showAlert("Please choose a different name");
        }
        event.consume();
    }

    public void redraw(){
        if(subSceneDisplayer!=null){
            if(viewModel!=null)
                subSceneDisplayer.setGame(viewModel.getGame(subSceneDisplayer.getCurrentType()));
            subSceneDisplayer.redraw();
        }
    }

    public void drawNewAgentLocation(){
        if(subSceneDisplayer!=null){
                subSceneDisplayer.drawTempAgent(x,y);
        }
    }

    public void newMap(){
        if(subSceneDisplayer!=null){
            subSceneDisplayer.setGame(viewModel.getGame(subSceneDisplayer.getCurrentType()));
            subSceneDisplayer.newMap();
        }
    }

    public void click(MouseEvent event){
        if(disableLabels()==false){
            double size=subSceneDisplayer.getSize();
            double x=event.getX()/size;
            double y=event.getY()/size;
            Position pos=new Position((int)x,(int)y);
            xIndex.setText("X: "+(int)x);
            yIndex.setText("Y: "+(int)y);
            // TODO: 29-Aug-18 : check valid location.
            if(event.getClickCount()>1 && subSceneDisplayer.getCurrentType()== IModel.Type.CREATE){
                if(clicked==false){
                    if(viewModel.validStart(pos)==false){
                        showAlert("Please pick a valid position for the new agent");
                        return;
                    }
                    if (confirm("Do you want to add a new Agent in the current location?\n if you press \"OK\" please double click on the target location.")){
                        clicked=true;
                        this.x=(int)x;
                        this.y=(int)y;
                        drawNewAgentLocation();
                    }
                }
                else if(clicked==true){
                    if(viewModel.validGoal(pos)==false || (this.x==(int)x && this.y==(int)y) ){
                        showAlert("Please pick a valid position for the new agent");
                        return;
                    }
                    if (confirm("Do you want to set the current location as the target of the new agent?")){
                        clicked=false;
                        viewModel.addAgent(new Position(this.x,this.y),new Position((int)x,(int)y),subSceneDisplayer.getCurrentType());
                    }
                }
            }
        }
    }

    private boolean disableLabels() {
        double size=subSceneDisplayer.getSize();
        if(size==0){
            xIndex.setText("X: ");
            yIndex.setText("Y: ");
            return true;
        }
        return false;
    }

    public boolean confirm(String str){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText(str);
        Optional<ButtonType> result = alert.showAndWait();
        return result.get() == ButtonType.OK;
    }

    public void zoomInOut(ScrollEvent scrollEvent) {
        try {
            double zoomFactor;
            if (scrollEvent.isControlDown()) {
                zoomFactor = 1.5;
                double deltaY = scrollEvent.getDeltaY();
                if (deltaY < 0) {
                    zoomFactor = 1 / zoomFactor;
                }
                subSceneDisplayer.changeSize(zoomFactor);
                scrollEvent.consume();
            }
        } catch (NullPointerException e) {
            scrollEvent.consume();
        }
    }

    public void randomMap(double[] arr) {
        viewModel.randomMap(arr);
    }

    public void randomClosed() {
        subSceneDisplayer.newMap();
        if(subSceneDisplayer.getCurrentType()== IModel.Type.CREATE && viewModel.getGame(IModel.Type.CREATE)!=null)
            createVbox.setVisible(true);
    }

    public void LoadInstance(ActionEvent actionEvent) {
        String path="";
            path="Created Files";
        File file=loadInstanceFile(path);
        if (file != null) {
            viewModel.loadInstance(file, subSceneDisplayer.getCurrentType());
            newMap();
        }
        if (subSceneDisplayer.getCurrentType()== IModel.Type.SIMULATE && viewModel.getGame(IModel.Type.SIMULATE)!=null)
            solBut.setVisible(true);

        actionEvent.consume();

    }

    private File loadInstanceFile(String location) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Load Instance");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("instance files", "*"));
        File file=checkIfExists(location);
        fc.setInitialDirectory(file);
        //showing the file chooser
        return fc.showOpenDialog(null);
    }
}
