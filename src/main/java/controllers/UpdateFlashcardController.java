package controllers;

import database.Database;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import utilities.Flashcard;
import utilities.User;

public class UpdateFlashcardController extends Controller
{
    public TextField frontInput;
    public TextArea backInput;
    public User user;
    public static int flashcardIndex;

    public void initialize()
    {
        this.user = User.getInstance();
        Flashcard flashcard = this.user.getFlashcards().get(flashcardIndex);
        this.frontInput.setText(flashcard.getFront());
        this.backInput.setText(flashcard.getBack());
    }

    public static void setFlashcardIndex(int flashcardIndex)
    {
        UpdateFlashcardController.flashcardIndex = flashcardIndex;
    }

    public void updateFlashcard(MouseEvent mouseEvent)
    {
        this.user.getFlashcards().get(flashcardIndex).setFront(this.frontInput.getText());
        this.user.getFlashcards().get(flashcardIndex).setBack(this.backInput.getText());
        Database.getInstance().updateFlashcard(this.user.getFlashcards().get(flashcardIndex));

        closeCurrentScene(mouseEvent);
        updateHomeScene();
    }
}
