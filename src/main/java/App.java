import database.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Paths;

public class App extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        URL fxmlUrl = Paths.get("./src/main/java/fxml/application.fxml").toUri().toURL();
        Parent root = FXMLLoader.load(fxmlUrl);
        Scene scene = new Scene(root);

        DatabaseManager db = new DatabaseManager();

        stage.setTitle("Flashcards");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}
