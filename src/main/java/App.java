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
        FXMLLoader loader = new FXMLLoader(Paths.get("./src/main/resources/scenes/sign-in.fxml").toUri().toURL());
        Scene homeScene = new Scene(loader.load());

        stage.setTitle("Flashcards");
        stage.setScene(homeScene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args)
    {
        launch();
    }
}
