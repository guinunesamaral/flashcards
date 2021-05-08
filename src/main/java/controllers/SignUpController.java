package controllers;

import database.Database;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

/*
 * TODO: when the user enter wrong data, I must show a message of correction, instead of
 *  just break the application
 * */

public class SignUpController extends Controller
{
    @FXML
    public TextField nameInput;
    @FXML
    public TextField passwordInput;
    @FXML
    public TextField emailInput;
    @FXML
    public TextField confirmPasswordInput;

    public void goToHomeScene(MouseEvent mouseEvent) throws IOException
    {
        switchScene(mouseEvent, HOME_SCENE);
    }

    public void validateRegistration(MouseEvent mouseEvent)
    {
        try {
            if (Database.getInstance()
                    .signUp(this.nameInput.getText(),
                            this.emailInput.getText(),
                            this.passwordInput.getText())) {
                goToHomeScene(mouseEvent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
