package controllers;

import database.UserDataReader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.w3c.dom.Element;

import java.io.IOException;
import java.nio.file.Paths;

public class HomeController extends Controller
{
    public static final String FLASHCARD_PATH = "./src/main/resources/components/flashcard.fxml";
    public Label scoreValue;
    public FlowPane flashcardBox;

    public void initialize()
    {
        fetchFlashcards();
    }

    public void goToNotificationsScene(MouseEvent mouseEvent)
    {
        switchScene(mouseEvent, NOTIFICATIONS_SCENE);
    }

    public void signOut(MouseEvent mouseEvent)
    {
        switchScene(mouseEvent, SIGN_IN_SCENE);
    }

    public void fetchFlashcards()
    {
        UserDataReader userDataReader = new UserDataReader();
        Element flashcards = (Element) userDataReader.userDataDocument.getElementsByTagName("flashcards").item(0);
        int flashcardListLength = flashcards.getElementsByTagName("flashcard").getLength();
        for (int flashcardIndex = 0; flashcardIndex < flashcardListLength; flashcardIndex++) {
            try {
                FXMLLoader loader = new FXMLLoader(Paths.get(FLASHCARD_PATH).toUri().toURL());
                FlowPane flashcardFxml = loader.load();
                FlashcardController flashcardController = loader.getController();
                flashcardController.setFlashcardProperties(flashcardIndex);
                this.flashcardBox.getChildren().add(flashcardFxml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void addFlashcard()
    {
        openScene(ADD_FLASHCARD_SCENE);
    }

    public void study()
    {
        openScene(STUDY_SCENE);
    }
}
