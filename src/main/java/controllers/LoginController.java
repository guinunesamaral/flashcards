package controllers;

import database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class LoginController extends Controller
{
    public static final String REGISTER_SCENE_PATH = "./src/main/java/screens/register.fxml";
    public static final String HOME_SCENE_PATH = "./src/main/java/screens/home.fxml";
    public static final String REDEFINE_PASSWORD_SCENE_PATH = "./src/main/java/screens/redefine-password.fxml";
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
        boolean isLoginSuccessful = dbManager.login(emailInput.getText(), passwordInput.getText());

        try {
            if (isLoginSuccessful) goToHomeScene(mouseEvent);
            else goToRegisterScene(mouseEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // This method is called by the validateLogin method, and it's not triggered by the register label
    public void goToRegisterScene(MouseEvent mouseEvent) throws IOException
    {
        switchScene(mouseEvent, REGISTER_SCENE_PATH);
    }

    @FXML
    public void goToHomeScene(MouseEvent mouseEvent) throws IOException
    {
        switchScene(mouseEvent, HOME_SCENE_PATH);
    }

    @FXML
    public void goToRedefinePasswordScene(MouseEvent mouseEvent) throws IOException
    {
        switchScene(mouseEvent, REDEFINE_PASSWORD_SCENE_PATH);
    }
}
