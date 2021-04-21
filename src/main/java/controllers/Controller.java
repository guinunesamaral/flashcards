package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

public class Controller
{
    public static final String LOGIN_SCENE = "./src/main/java/scenes/login.fxml";
    public static final String REGISTER_SCENE = "./src/main/java/scenes/register.fxml";
    public static final String REDEFINE_PASSWORD_SCENE = "./src/main/java/scenes/redefine-password.fxml";
    public static final String NOTIFICATIONS_SCENE = "./src/main/java/scenes/notifications.fxml";
    public static final String HOME_SCENE = "./src/main/java/scenes/home.fxml";
    public static final String SHARE_FLASHCARD = "./src/main/java/scenes/share-flashcard.fxml";
    public static final String UPDATE_FLASHCARD = "./src/main/java/scenes/update-flashcard.fxml";
    public static final String USER_DATA_PATH = "./src/main/java/userdata/user-data.xml";

    public void switchScene(MouseEvent mouseEvent, String sceneFxmlPath)
    {
        try {
            Scene scene = new Scene(FXMLLoader.load(Paths.get(sceneFxmlPath).toUri().toURL()));
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
