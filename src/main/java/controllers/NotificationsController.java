package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.FlowPane;
import utilities.Flashcard;
import utilities.User;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class NotificationsController extends Controller
{
    public FlowPane flashcardBox;

    public void initialize()
    {
        fetchFlashcards();
    }

    public void fetchFlashcards()
    {
        User user = User.getInstance();
        ArrayList<Flashcard> flashcards = user.getReceivedFlashcards();

        int flashcardsLength = flashcards.size() - 1;
        for (int flashcardIndex = 0; flashcardIndex <= flashcardsLength; flashcardIndex++) {
            try {
                FXMLLoader loader = new FXMLLoader(Paths.get(RECEIVED_FLASHCARD).toUri().toURL());
                FlowPane flashcardFxml = loader.load();
                ReceivedFlashcardController receivedFlashcardController = loader.getController();
                receivedFlashcardController.setFlashcardProperties(flashcardIndex);
                this.flashcardBox.getChildren().add(flashcardFxml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}