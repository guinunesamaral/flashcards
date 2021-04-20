package controllers;

import database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginController extends Controller
{
    @FXML
    public Button signInButton;
    @FXML
    public Label lostPasswordLabel;
    @FXML
    public Label registerSceneLabel;
    @FXML
    public TextField emailInput;
    @FXML
    public TextField passwordInput;

    @FXML
    public void validateLogin(MouseEvent mouseEvent)
    {
        DatabaseManager dbManager = new DatabaseManager();
        /*
        * When the login method is executed it generates a user-data.xml file, which
        * contains all user data and his flashcards data
        * */
        boolean isLoginSuccessful = dbManager.login(emailInput.getText(), passwordInput.getText());

        if (isLoginSuccessful) goToHomeScene(mouseEvent);
        else goToRegisterScene(mouseEvent);
    }

    // This method is called by the validateLogin method, and it's not triggered by the register label
    public void goToRegisterScene(MouseEvent mouseEvent)
    {
        switchScene(mouseEvent, REGISTER_SCENE_PATH);
    }

    @FXML
    public void goToHomeScene(MouseEvent mouseEvent)
    {
        switchScene(mouseEvent, HOME_SCENE_PATH);
    }

    @FXML
    public void goToRedefinePasswordScene(MouseEvent mouseEvent)
    {
        switchScene(mouseEvent, REDEFINE_PASSWORD_SCENE_PATH);
    }
}
