package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
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
        fetchFlashcards();
        updateScore();
        checkNotifications();
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
                flashcardController.setFlashcardProperties(flashcardIndex);
                this.flashcardBox.getChildren().add(flashcardFxml);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void openNotificationsScene()
    {
        openScene(NOTIFICATIONS_SCENE, "Receive flashcard");
    }

    public void openAddFlashcardScene()
    {
        openScene(ADD_FLASHCARD_SCENE, "New flashcard");
    }

    public void openStudyScene()
    {
        Stage stage = openScene(STUDY_SCENE, "Study");
        stage.setOnCloseRequest(windowEvent -> updateHomeScene());
    }
}
