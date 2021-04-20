import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.nio.file.Paths;

public class App extends Application
{
    @Override
    public void start(Stage stage) throws Exception
    {
        FXMLLoader loginFXML = new FXMLLoader(Paths.get("./src/main/java/scenes/home.fxml").toUri().toURL());
        Scene loginScene = new Scene(loginFXML.load());

        stage.setTitle("Flashcards");
        stage.setScene(loginScene);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}
