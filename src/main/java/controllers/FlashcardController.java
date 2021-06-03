package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import utilities.Flashcard;
import utilities.User;

import java.io.IOException;
import java.nio.file.Paths;

public class FlashcardController extends Controller
{
    public AnchorPane flashcardAnchorPane;
    public Label flashcardFront;
    public User user;
    private int flashcardIndex;

    public void initialize()
    {
        this.user = User.getInstance();
    }

    public void setFlashcardProperties(int flashcardIndex)
    {
        this.flashcardIndex = flashcardIndex;
        Flashcard flashcard = this.user.getFlashcards().get(this.flashcardIndex);
        this.flashcardFront.setText(flashcard.getFront());
    }

    public void expandFlashcard()
    {
        try {
            FXMLLoader loader = new FXMLLoader(Paths.get(EXPANDED_FLASHCARD_SCENE).toUri().toURL());
            loader.load();
            ExpandedFlashcardController expandedFlashcardController = loader.getController();
            expandedFlashcardController.setFlashcardIndex(this.flashcardIndex);
        } catch (IOException e) {
            e.printStackTrace();
        }
        openScene(EXPANDED_FLASHCARD_SCENE, "Flashcard");
    }
}
