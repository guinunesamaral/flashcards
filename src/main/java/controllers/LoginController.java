package controllers;

import database.DatabaseManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.nio.file.Paths;

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
        boolean isLoginSuccessful = dbManager.login(emailInput.getText(), passwordInput.getText());

        if (isLoginSuccessful) {
            try {
                goToHomeScene(mouseEvent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                goToRegisterScene(mouseEvent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // This method is called by the validateLogin method, and it's not triggered by the register label
    public void goToRegisterScene(MouseEvent mouseEvent) throws IOException
    {
        FXMLLoader registerFXML = new FXMLLoader(Paths.get("./src/main/java/fxml/register.fxml").toUri().toURL());
        switchScene(mouseEvent, registerFXML);
    }

    @FXML
    public void goToHomeScene(MouseEvent mouseEvent) throws IOException
    {
        FXMLLoader homeFXML = new FXMLLoader(Paths.get("./src/main/java/fxml/home.fxml").toUri().toURL());
        switchScene(mouseEvent, homeFXML);
    }

    @FXML
    public void goToRedefinePasswordScene(MouseEvent mouseEvent) throws IOException
    {
        FXMLLoader recoverPasswordFXML = new FXMLLoader(Paths.get("./src/main/java/fxml/redefine-password.fxml").toUri().toURL());
        switchScene(mouseEvent, recoverPasswordFXML);
    }
}
