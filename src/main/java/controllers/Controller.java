package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Paths;

/*
 * TODO: To fix the problem of openSceneAndWait is simple, it only the method to return a boolean
 *  value, that will only be returned when the new stage is closed. It means that the refresh button
 *  can be discarded
 * */

public class Controller
{
    public static final String SIGN_IN_SCENE = "./src/main/resources/scenes/sign-in.fxml";
    public static final String SIGN_UP_SCENE = "./src/main/resources/scenes/sign-up.fxml";
    public static final String REDEFINE_PASSWORD_SCENE = "./src/main/resources/scenes/redefine-password.fxml";
    public static final String HOME_SCENE = "./src/main/resources/scenes/home.fxml";
    public static final String NOTIFICATIONS_SCENE = "./src/main/resources/scenes/notifications.fxml";
    public static final String STUDY_ALL_FLASHCARDS_SCENE = "./src/main/resources/scenes/study-all-flashcards.fxml";
    public static final String STUDY_ONE_FLASHCARD_SCENE = "./src/main/resources/scenes/study-one-flashcard.fxml";
    public static final String ADD_FLASHCARD_SCENE = "./src/main/resources/scenes/add-flashcard.fxml";
    public static final String FLASHCARD = "./src/main/resources/components/flashcard.fxml";
    public static final String RECEIVE_FLASHCARD = "./src/main/resources/components/receive-flashcard.fxml";
    public static final String EXPANDED_FLASHCARD_SCENE = "./src/main/resources/components/expanded-flashcard.fxml";
    public static final String SHARE_FLASHCARD_SCENE = "./src/main/resources/scenes/share-flashcard.fxml";
    public static final String UPDATE_FLASHCARD_SCENE = "./src/main/resources/scenes/update-flashcard.fxml";

    public void switchScene(MouseEvent mouseEvent,
                            String sceneFxmlPath)
    {
        try {
            Scene scene = new Scene(FXMLLoader.load(Paths.get(sceneFxmlPath).toUri().toURL()));
            Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void openSceneAndWait(String sceneFxmlPath,
                                 String stageTitle)
    {
        try {
            Stage stage = new Stage();
            Scene scene = new Scene(FXMLLoader.load(Paths.get(sceneFxmlPath).toUri().toURL()));
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle(stageTitle);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeCurrentScene(MouseEvent mouseEvent)
    {
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.close();
    }
}
