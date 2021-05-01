package controllers;

import database.UserDataReader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.w3c.dom.Element;

import java.io.IOException;
import java.nio.file.Paths;

public class Home extends Controller
{
    public static final String FLASHCARD_PATH = "./src/main/java/components/flashcard.fxml";
    public FlowPane flashcardBox;

    public void initialize()
    {
        addFlashcards();
    }

    public void goToNotificationsScene(MouseEvent mouseEvent)
    {
        switchScene(mouseEvent, NOTIFICATIONS_SCENE);
    }

    public void exitApplication(MouseEvent mouseEvent)
    {
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.close();
    }

    public void addFlashcards()
    {
        UserDataReader userDataReader = new UserDataReader();
        Element flashcards = (Element) userDataReader.userDataDocument.getElementsByTagName("flashcards").item(0);
        int flashcardListLength = flashcards.getElementsByTagName("flashcard").getLength();
        for (int flashcardIndex = 0; flashcardIndex < flashcardListLength; flashcardIndex++) {
            try {
                FXMLLoader loader = new FXMLLoader(Paths.get(FLASHCARD_PATH).toUri().toURL());
                FlowPane flashcardFxml = loader.load();
                Flashcard flashcardController = loader.getController();
                flashcardController.setFlashcardProperties(flashcardIndex);
                this.flashcardBox.getChildren().add(flashcardFxml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
