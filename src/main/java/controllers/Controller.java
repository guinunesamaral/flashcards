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
    public static final String REGISTER_SCENE_PATH = "./src/main/java/scenes/register.fxml";
    public static final String HOME_SCENE_PATH = "./src/main/java/scenes/home.fxml";
    public static final String REDEFINE_PASSWORD_SCENE_PATH = "./src/main/java/scenes/redefine-password.fxml";

    public void switchScene(MouseEvent mouseEvent, String sceneFxmlPath)
    {
        try {
            FXMLLoader sceneFXML = new FXMLLoader(Paths.get(sceneFxmlPath).toUri().toURL());
            Scene scene = new Scene(sceneFXML.load());
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
