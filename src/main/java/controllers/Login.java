package controllers;

import database.Database;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Login extends Controller
{
    @FXML
    public TextField emailInput;
    @FXML
    public TextField passwordInput;

    @FXML
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

    @FXML
    private void goToRegisterScene(MouseEvent mouseEvent)
    {
        switchScene(mouseEvent, REGISTER_SCENE);
    }

    @FXML
    public void goToHomeScene(MouseEvent mouseEvent)
    {
        switchScene(mouseEvent, HOME_SCENE);
    }

    @FXML
    public void goToRedefinePasswordScene(MouseEvent mouseEvent)
    {
        switchScene(mouseEvent, REDEFINE_PASSWORD_SCENE);
    }

    public void exitApp(MouseEvent mouseEvent)
    {
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.close();
    }
}
