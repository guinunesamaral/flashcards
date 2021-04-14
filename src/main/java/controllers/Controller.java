package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller
{
    public void switchScene(MouseEvent mouseEvent, FXMLLoader sceneFXML) throws IOException
    {
        Scene scene = new Scene(sceneFXML.load());
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
