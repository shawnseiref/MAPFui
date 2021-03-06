package frontend.view;

import frontend.model.*;
import frontend.viewmodel.ViewModel;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.Optional;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            Model model = new Model();
            ViewModel viewModel = new ViewModel(model);
            model.addObserver(viewModel);
            //--------------
            primaryStage.setTitle("Multi-Agent Path Find - User Interface");
            FXMLLoader fxmlLoader = new FXMLLoader();
            Parent root = fxmlLoader.load(getClass().getResource("View.fxml").openStream());
            Scene scene = new Scene(root, 800, 700);
            primaryStage.setScene(scene);
            //--------------
            ViewController view = fxmlLoader.getController();
            view.setViewModel(viewModel);
            viewModel.addObserver(view);
            //--------------
            SetStageCloseEvent(primaryStage);
            primaryStage.show();
        }
        catch (Exception exception){
            exception.printStackTrace();
        }
    }

    private void SetStageCloseEvent(Stage primaryStage) {
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent windowEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    // Close program
                } else {
                    // ... user chose CANCEL or closed the dialog
                    windowEvent.consume();
                }
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
