package controllers;

import database.UserDataReader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.Stage;
import org.w3c.dom.Element;

import java.io.IOException;
import java.nio.file.Paths;

public class HomeController extends Controller
{
    public static final String NOTIFICATIONS_SCENE_PATH = "./src/main/java/scenes/notifications.fxml";
    public static final String USER_DATA_PATH = "./src/main/java/userdata/user-data.xml";
    public static final String FLASHCARD_PATH = "./src/main/java/components/flashcard.fxml";

    @FXML
    public AnchorPane App;
    @FXML
    public AnchorPane header;
    @FXML
    public Button notificationsBtn;
    @FXML
    public Button exitBtn;
    @FXML
    public ChoiceBox flashcardsThemes;
    @FXML
    public FlowPane flashcardBox;

    public UserDataReader xmlReader;
    public String userId;

    @FXML
    public void initialize()
    {
        this.xmlReader = new UserDataReader(USER_DATA_PATH, "user");
        this.userId = this.xmlReader.getTagTextContent(this.xmlReader.userDataRootNode, "_id");
        addFlashcards();
    }

    @FXML
    public void goToNotificationsScene(MouseEvent mouseEvent)
    {
        switchScene(mouseEvent, NOTIFICATIONS_SCENE_PATH);
    }

    @FXML
    public void exitApplication(MouseEvent mouseEvent)
    {
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.close();
    }

    public void addFlashcards()
    {
        Element flashcards = (Element) this.xmlReader.userDataDocument.getElementsByTagName("flashcards").item(0);
        int flashcardListLength = flashcards.getElementsByTagName("flashcard").getLength();
        for (int index = 0; index < flashcardListLength; index++) {
            FlowPane flashcardFxml = null;
            try {
                FXMLLoader loader = new FXMLLoader(Paths.get(FLASHCARD_PATH).toUri().toURL());
                flashcardFxml = loader.load();
                FlashcardController flashcardController = loader.getController();
                flashcardController.setIndex(index);
                flashcardController.setFlashcardProperties();
            } catch (IOException e) {
                e.printStackTrace();
            }
            this.flashcardBox.getChildren().add(flashcardFxml);
        }
    }
}
