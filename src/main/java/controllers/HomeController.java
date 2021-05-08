package controllers;

import database.Database;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import utilities.Flashcard;
import utilities.Score;
import utilities.User;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class HomeController extends Controller
{
    public Button notificationsBtn;
    public FlowPane flashcardBox;
    public Label hardScoreValue;
    public Label easyScoreValue;

    public void initialize()
    {
        Database.getInstance().signIn("guinucode@gmail.com", "123456");
        fetchFlashcards();
        checkNotifications();
    }

    public void refreshScene()
    {
        removeFlashcardsFromBox();
        fetchFlashcards();
        updateScore();
    }

    public void signOut(MouseEvent mouseEvent)
    {
        User user = User.getInstance();
        user.setId(null);
        user.setName(null);
        user.setEmail(null);
        user.setPassword(null);
        user.getFlashcards().removeAll(user.getFlashcards());

        Score.easy = 0;
        Score.hard = 0;
        switchScene(mouseEvent, SIGN_IN_SCENE);
    }

    public void updateScore()
    {
        this.hardScoreValue.setText(String.valueOf(Score.hard));
        this.easyScoreValue.setText(String.valueOf(Score.easy));
    }

    public void removeFlashcardsFromBox()
    {
        if (this.flashcardBox.getChildren().size() > 0) {
            this.flashcardBox.getChildren().removeAll(this.flashcardBox.getChildren());
        }
    }

    public void checkNotifications()
    {
        if (User.getInstance().getReceivedFlashcards().size() > 0) {
            this.notificationsBtn.getStyleClass().remove("notifications-btn-normal");
            this.notificationsBtn.getStyleClass().add("notifications-btn-notification");
        }
        else {
            this.notificationsBtn.getStyleClass().add("notifications-btn-normal");
            this.notificationsBtn.getStyleClass().remove("notifications-btn-notification");
        }
    }

    public void fetchFlashcards()
    {
        User user = User.getInstance();
        ArrayList<Flashcard> flashcards = user.getFlashcards();

        int flashcardsLength = flashcards.size() - 1;
        for (int flashcardIndex = 0; flashcardIndex <= flashcardsLength; flashcardIndex++) {
            try {
                FXMLLoader loader = new FXMLLoader(Paths.get(FLASHCARD).toUri().toURL());
                FlowPane flashcardFxml = loader.load();
                FlashcardController flashcardController = loader.getController();
                flashcardController.setFlashcardIndex(flashcardIndex);
                this.flashcardBox.getChildren().add(flashcardFxml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void openNotificationsScene()
    {
        openSceneAndWait(NOTIFICATIONS_SCENE, "Receive flashcard");
    }

    public void openAddFlashcardScene()
    {
        openSceneAndWait(ADD_FLASHCARD_SCENE, "New flashcard");
    }

    public void openStudyAllFlashcardsScene()
    {
        openSceneAndWait(STUDY_ALL_FLASHCARDS_SCENE, "Study");
    }

    public void openStudyOneFlashcardScene()
    {
        openSceneAndWait(STUDY_ONE_FLASHCARD_SCENE, "Study");
    }
}
