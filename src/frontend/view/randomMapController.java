package frontend.view;

import java.awt.event.ActionEvent;

import frontend.model.IModel;
import javafx.stage.Stage;

public class randomMapController {
    public javafx.scene.control.TextField rows;
    public javafx.scene.control.TextField columns;
    public javafx.scene.control.TextField prob;
    private ViewController viewController;

    public void setViewController(ViewController viewController) {
        this.viewController = viewController;
    }

    public void generate(javafx.event.ActionEvent event) {
        try{
            double[] arr={Integer.parseInt(rows.getText()),Integer.parseInt(columns.getText()),Double.parseDouble(prob.getText())/100};
            viewController.randomMap(arr);
            close();
        }
        catch (Exception e){
            // TODO: 03-Sep-18 finish the alert
            viewController.showAlert("Insert a round number of 2...");
        }
    }

    private void close() {
        Stage stage = (Stage) prob.getScene().getWindow();
        stage.close();
        viewController.randomClosed();
    }
}
