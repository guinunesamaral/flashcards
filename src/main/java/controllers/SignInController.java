package controllers;

import database.Database;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class SignInController extends Controller
{
    public TextField emailInput;
    public TextField passwordInput;

    public void validateLogin(MouseEvent mouseEvent)
    {
        Database dbManager = Database.getInstance();
        /*
        * When the login method is executed it generates a user-data.xml file, which
        * contains all user data and his flashcards data
        * */
        boolean isLoginSuccessful = dbManager.login(emailInput.getText(), passwordInput.getText());
        if (isLoginSuccessful) goToHomeScene(mouseEvent);
    }

    public void goToHomeScene(MouseEvent mouseEvent)
    {
        switchScene(mouseEvent, HOME_SCENE);
    }

    @FXML
    private void goToSignUpScene(MouseEvent mouseEvent)
    {
        switchScene(mouseEvent, SIGN_UP_SCENE);
    }

    public void goToRedefinePasswordScene(MouseEvent mouseEvent)
    {
        switchScene(mouseEvent, REDEFINE_PASSWORD_SCENE);
    }
}
