package controllers;

import javafx.animation.RotateTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import utilities.Flashcard;
import utilities.Score;
import utilities.User;

import java.util.ArrayList;

/*
 * TODO: When I sort the index number of the flashcard, I must guarantee that it isn't
 *  a repeated number
 * TODO: The flashcardBox has a limited number of flashcards, so the study must end according
 *  to the number of flashcards. For now, the study never ends
 * */

public class StudyAllFlashcardsController extends Controller
{
    public Pane flashcardPane;
    public Label flashcardFront;
    public Label flashcardBack;
    public Button showBtn;
    public Button easyBtn;
    public Button hardBtn;
    public ArrayList<Flashcard> flashcards;
    public int flashcardsLength;
    public Flashcard currentFlashcard;

    public void initialize()
    {
        this.flashcards = User.getInstance().getFlashcards();
        this.flashcardsLength = this.flashcards.size() - 1;
        setFlashcard(sortFlashcard());
    }

    public void nextFlashcard()
    {
        int index = this.flashcards.indexOf(this.currentFlashcard) + 1;
        if (index > this.flashcardsLength) {
            index = 0;
        }
        resetFlashcard();
        setFlashcard(index);
    }

    public void previousFlashcard()
    {
        int index = this.flashcards.indexOf(this.currentFlashcard) - 1;
        if (index < 0) {
            index = this.flashcardsLength;
        }
        resetFlashcard();
        setFlashcard(index);
    }

    public void setFlashcard(int index)
    {
        this.currentFlashcard = this.flashcards.get(index);
        this.flashcardFront.setText(this.currentFlashcard.getFront());
        this.flashcardBack.setText(this.currentFlashcard.getBack());
        showFlashcardFront();
    }

    public void resetFlashcard()
    {
        if (!this.flashcardFront.visibleProperty().get()) {
            this.flashcardFront.visibleProperty().set(true);
            this.flashcardBack.visibleProperty().set(false);
        }
        if (this.showBtn.getText().equals("Show front")) {
            this.showBtn.setText("Show back");
            easyBtn.setVisible(false);
            hardBtn.setVisible(false);
        }
        if (this.flashcardPane.getStyleClass().contains("back-bg")) {
            this.flashcardPane.getStyleClass().remove("back-bg");
            this.flashcardPane.getStyleClass().add("front-bg");
        }
    }

    public void showFlashcardFront()
    {
        if (this.flashcardPane.getStyleClass().contains("back-bg")) {
            this.flashcardPane.getStyleClass().remove("back-bg");
            this.flashcardPane.getStyleClass().add("front-bg");
        }
    }

    public int sortFlashcard()
    {
        return (int) (Math.random() * (this.flashcardsLength + 1));
    }

    public void updateEasyScore()
    {
        Score.easy += 1;
        nextFlashcard();
    }

    public void updateHardScore()
    {
        Score.hard += 1;
        nextFlashcard();
    }

    public void flipFlashcard()
    {
        flipFlashcard(this.flashcardPane,
                this.flashcardFront,
                this.flashcardBack,
                this.showBtn,
                this.easyBtn,
                this.hardBtn);
    }

    static void flipFlashcard(Pane flashcardPane,
                              Label flashcardFront,
                              Label flashcardBack,
                              Button showBtn,
                              Button easyBtn,
                              Button hardBtn)
    {
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setDuration(Duration.millis(1000));
        rotateTransition.setAxis(Rotate.Y_AXIS);
        rotateTransition.setCycleCount(1);
        rotateTransition.setNode(flashcardPane);

        rotateTransition.play();
        if (flashcardFront.visibleProperty().get()) {
            flashcardFront.visibleProperty().set(false);
            flashcardBack.visibleProperty().set(true);
            showBtn.setText("Show front");
            easyBtn.setVisible(true);
            hardBtn.setVisible(true);
        } else {
            flashcardFront.visibleProperty().set(true);
            flashcardBack.visibleProperty().set(false);
            showBtn.setText("Show back");
            easyBtn.setVisible(false);
            hardBtn.setVisible(false);
        }
        if (flashcardPane.getStyleClass().contains("front-bg")) {
            flashcardPane.getStyleClass().remove("front-bg");
            flashcardPane.getStyleClass().add("back-bg");
        } else {
            flashcardPane.getStyleClass().remove("back-bg");
            flashcardPane.getStyleClass().add("front-bg");
        }
    }
}
