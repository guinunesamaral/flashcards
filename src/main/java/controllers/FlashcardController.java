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

    public void setFlashcardIndex(int flashcardIndex)
    {
        this.flashcardIndex = flashcardIndex;
    }

    public void setFlashcardProperties()
    {
        Flashcard flashcard = this.user.getFlashcards().get(flashcardIndex);
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
        openSceneAndWait(EXPANDED_FLASHCARD_SCENE, "Flashcard");
    }
}
