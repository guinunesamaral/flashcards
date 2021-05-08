package controllers;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import utilities.Flashcard;
import utilities.Score;
import utilities.User;

import java.util.ArrayList;

public class StudyOneFlashcardController extends Controller
{
    public Pane flashcardPane;
    public Label flashcardFront;
    public Label flashcardBack;
    public Button showBtn;
    public Button easyBtn;
    public Button hardBtn;
    public ArrayList<Flashcard> flashcards;

    public void initialize()
    {
        this.flashcards = User.getInstance().getFlashcards();
        setFlashcard(sortFlashcard());
    }

    public void setFlashcard(Flashcard flashcard)
    {
        this.flashcardFront.setText(flashcard.getFront());
        this.flashcardBack.setText(flashcard.getBack());
        showFlashcardFront();
    }

    public void showFlashcardFront()
    {
        if (this.flashcardPane.getStyleClass().contains("back-bg")) {
            this.flashcardPane.getStyleClass().remove("back-bg");
            this.flashcardPane.getStyleClass().add("front-bg");
        }
    }

    public Flashcard sortFlashcard()
    {
        int flashcardsLength = this.flashcards.size() - 1;
        int randomIndex=  (int) (Math.random() * (flashcardsLength + 1));
        return this.flashcards.get(randomIndex);
    }

    public void updateEasyScore(MouseEvent mouseEvent)
    {
        Score.easy += 1;
        closeCurrentScene(mouseEvent);
    }

    public void updateHardScore(MouseEvent mouseEvent)
    {
        Score.hard += 1;
        closeCurrentScene(mouseEvent);
    }

    public void flipFlashcard()
    {
        StudyAllFlashcardsController.flipFlashcard(this.flashcardPane,
                this.flashcardFront,
                this.flashcardBack,
                this.showBtn,
                this.easyBtn,
                this.hardBtn);
    }
}
