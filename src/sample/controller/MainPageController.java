package sample.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.fxml.FXMLLoader;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.stage.Stage;

////


public class MainPageController {

    @FXML
    private TextField registrationFormName;

    @FXML
    private TextField registrationFormFeet;

    @FXML
    private TextField registrationFormWeight;

    @FXML
    private TextField registrationFormInches;

    @FXML
    private Button registrationFormCalculateBtn;

    @FXML
    private Label mainPageErrorMessage;

    // intitialize function for the main page
    @FXML
    void initialize() {
        //Network object created
        Network network = new Network();
        //event listener for the Calculate button
        registrationFormCalculateBtn.setOnAction(event -> {
            // variables to store user input info
            String name = registrationFormName.getText().trim();
            String feet = registrationFormFeet.getText().trim();
            String inches = registrationFormInches.getText().trim();
            String weight = registrationFormWeight.getText().trim();
            //string to send to the Server
            String line = name + "#" + feet + "#" + inches + "#" + weight;
            //passing the string to the Network object variable
            network.setShareOut(line);
            //calling startNetwork function of the Network obj
            network.startNetwork();
            //Filters for user input
            if (name.equals("") || feet.equals("") || inches.equals("") || weight.equals("")){
                mainPageErrorMessage.setText("Error: please fill all the fields.");
            } else if(!isNum(feet) || !isNum(inches) || !isNum(weight)){
                mainPageErrorMessage.setText("Error: please enter only integer numbers");
            } else if((Integer.parseInt(feet) <= 0 && Integer.parseInt(inches) <= 0) || Integer.parseInt(weight) <= 0){
                mainPageErrorMessage.setText("Error: value can't be negative or zero");
            }else if(name.indexOf('#') != -1){
                mainPageErrorMessage.setText("Error: name can't contain symbol '#'");
            }else{
                // if input is valid changing the window to result form
                mainPageErrorMessage.setText("");
                registrationFormCalculateBtn.getScene().getWindow().hide();
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/view/resultForm.fxml"));
                try {
                    loader.load();
                } catch (IOException e){
                    e.printStackTrace();
                }
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.show();
            }
        });

    }

    // helper function to determine if the string is a valid int
    private boolean isNum(String str){
        boolean result = true;
        for (int i = 0; i < str.length(); i++){
            if (!Character.isDigit(str.charAt(i))){
                result = false;
                break;
            }
        }
        return result;
    }
}
