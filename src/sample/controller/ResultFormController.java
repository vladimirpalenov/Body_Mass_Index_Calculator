package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.stage.Stage;


public class ResultFormController {

    @FXML
    private Label resultFormBMI;

    @FXML
    private Label resultFormDescription1;

    @FXML
    private Label resultFormDescription2;

    @FXML
    private Button resultFormBackBtn;

    @FXML
    private Label resultFormGreetingLabel;

    @FXML
    void initialize() {
        // Accessing the string received from the Server
        String display = Network.getShareIn();
        //Splitting the string into array by delimiter
        String arrReceived[] = display.split("#");
        //Setting the text of labels to the received data
        resultFormGreetingLabel.setText(arrReceived[0]);
        resultFormBMI.setText(arrReceived[1]);
        resultFormDescription1.setText(arrReceived[2]);
        resultFormDescription2.setText(arrReceived[3]);
        // Implementing the Back button
        resultFormBackBtn.setOnAction(event -> {
            resultFormGreetingLabel.setText("");
            resultFormBMI.setText("");
            resultFormDescription1.setText("");
            resultFormDescription2.setText("");
            resultFormBackBtn.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/sample/view/mainPage.fxml"));
            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });

    }

}
