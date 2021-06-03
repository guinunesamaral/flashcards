package controllers;

import database.Database;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import utilities.Flashcard;
import utilities.User;

public class ReceiveFlashcardController extends Controller
{
    public AnchorPane flashcardAnchorPane;
    public Label flashcardFront;
    public Label flashcardBack;
    public Pane flashcardToolBar;
    public User user;
    public static int flashcardIndex;

    public void initialize()
    {
        this.user = User.getInstance();
    }

    public void setFlashcardProperties(int flashcardIndex)
    {
        ReceiveFlashcardController.flashcardIndex = flashcardIndex;
        Flashcard flashcard = this.user.getFlashcards().get(flashcardIndex);
        this.flashcardFront.setText(flashcard.getFront());
        this.flashcardBack.setText(flashcard.getBack());
    }

    public void flipFlashcard()
    {
        ExpandedFlashcardController
                .flip(this.flashcardAnchorPane, this.flashcardFront, this.flashcardBack, this.flashcardToolBar);
    }

    public void acceptFlashcard()
    {
        Database.getInstance().acceptFlashcard(flashcardIndex);
    }

    public void refuseFlashcard()
    {
        Database.getInstance().refuseFlashcard(flashcardIndex);
    }

    public void accepAllFlashcards()
    {

    }
}
