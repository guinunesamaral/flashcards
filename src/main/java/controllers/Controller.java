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
    public void switchScene(MouseEvent mouseEvent, String sceneFxmlPath) throws IOException
    {
        FXMLLoader sceneFXML = new FXMLLoader(Paths.get(sceneFxmlPath).toUri().toURL());
        Scene scene = new Scene(sceneFXML.load());
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
