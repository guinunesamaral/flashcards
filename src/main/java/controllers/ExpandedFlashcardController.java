package controllers;

import database.Database;
import javafx.animation.RotateTransition;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import utilities.Flashcard;
import utilities.User;

public class ExpandedFlashcardController extends Controller
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
        setFlashcardProperties();
    }

    public void setFlashcardIndex(int flashcardIndex)
    {
        ExpandedFlashcardController.flashcardIndex = flashcardIndex;
    }

    public void setFlashcardProperties()
    {
        Flashcard flashcard = user.getFlashcards().get(flashcardIndex);
        this.flashcardFront.setText(flashcard.getFront());
        this.flashcardBack.setText(flashcard.getBack());
    }

    public void flipFlashcard()
    {
        flip(flashcardAnchorPane, this.flashcardFront, this.flashcardBack, this.flashcardToolBar);
    }

    static void flip(AnchorPane flashcardAnchorPane,
                     Label flashcardFront,
                     Label flashcardBack,
                     Pane flashcardToolBar)
    {
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setDuration(Duration.millis(1000));
        rotateTransition.setAxis(Rotate.Y_AXIS);
        rotateTransition.setCycleCount(1);
        rotateTransition.setNode(flashcardAnchorPane);

        rotateTransition.play();
        if (flashcardFront.visibleProperty().get()) {
            flashcardFront.visibleProperty().set(false);
            flashcardBack.visibleProperty().set(true);
            flashcardToolBar.visibleProperty().set(false);
        } else {
            flashcardFront.visibleProperty().set(true);
            flashcardBack.visibleProperty().set(false);
            flashcardToolBar.visibleProperty().set(true);
        }
        if (flashcardAnchorPane.getStyleClass().contains("front-bg")) {
            flashcardAnchorPane.getStyleClass().remove("front-bg");
            flashcardAnchorPane.getStyleClass().add("back-bg");
        } else {
            flashcardAnchorPane.getStyleClass().remove("back-bg");
            flashcardAnchorPane.getStyleClass().add("front-bg");
        }
    }

    public void removeFlashcard(MouseEvent mouseEvent)
    {
        Database.getInstance().removeFlashcard(this.user.getFlashcards().get(flashcardIndex));
        this.user.getFlashcards().remove(flashcardIndex);
        closeCurrentScene(mouseEvent);
        updateHomeScene();
    }

    public void openShareFlashcardScene(MouseEvent mouseEvent)
    {
        ShareFlashcardController.setFlashcardIndex(flashcardIndex);
        openScene(SHARE_FLASHCARD_SCENE, "Share");
        closeCurrentScene(mouseEvent);
    }

    public void openUpdateFlashcardScene(MouseEvent mouseEvent)
    {
        UpdateFlashcardController.setFlashcardIndex(flashcardIndex);
        openScene(UPDATE_FLASHCARD_SCENE, "Update");
        closeCurrentScene(mouseEvent);
    }
}
