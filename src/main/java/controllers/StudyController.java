package controllers;

import javafx.animation.RotateTransition;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
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

public class StudyController extends Controller
{
    public Pane flashcardPane;
    public Label flashcardFront;
    public Label flashcardBack;
    public Button showBtn;
    public Button easyBtn;
    public Button hardBtn;
    public ArrayList<Flashcard> flashcards;
    public Flashcard currentFlashcard;

    public void initialize()
    {
        this.flashcards = new ArrayList<>();
        this.flashcards.addAll(User.getInstance().getFlashcards());
        setFlashcard(sortFlashcard());
        resetScore();
    }

    public void resetScore()
    {
        Score.easy = 0;
        Score.hard = 0;
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
        int nbrOfFlashcards = this.flashcards.size();
        return (int) (Math.random() * (nbrOfFlashcards));
    }

    public void setFlashcard(int index)
    {
        this.currentFlashcard = this.flashcards.get(index);
        this.flashcards.remove(index);
        this.flashcardFront.setText(this.currentFlashcard.getFront());
        this.flashcardBack.setText(this.currentFlashcard.getBack());
        showFlashcardFront();
    }

    public void updateEasyScore(MouseEvent mouseEvent)
    {
        Score.easy += 1;
        resetFlashcard();
        setFlashcard(sortFlashcard());
        if (this.flashcards.size() == 0) {
            updateHomeScene();
            closeCurrentScene(mouseEvent);
        }
    }

    public void updateHardScore(MouseEvent mouseEvent)
    {
        Score.hard += 1;
        resetFlashcard();
        setFlashcard(sortFlashcard());
        if (this.flashcards.size() == 0) {
            updateHomeScene();
            closeCurrentScene(mouseEvent);
        }
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
