package controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.nio.file.Paths;

public class RegisterController extends Controller
{
    @FXML
    public TextField nameInput;
    @FXML
    public TextField passwordInput;
    @FXML
    public TextField emailInput;
    @FXML
    public TextField confirmPasswordInput;
    @FXML
    public Button registerBtn;

    public static final String VALID_EMAIL = "";

    public void goToHomeScene(MouseEvent mouseEvent) throws IOException
    {
        FXMLLoader homeFXML = new FXMLLoader(Paths.get("./src/main/java/fxml/home.fxml").toUri().toURL());
        switchScene(mouseEvent, homeFXML);
    }

    public void validateRegistration(MouseEvent mouseEvent)
    {
        try {
            goToHomeScene(mouseEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
