package controllers;

import database.XMLReader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
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
    public static final String NOTIFICATIONS_SCENE_PATH = "./src/main/java/screens/notifications.fxml";
    public static final String USER_DATA_PATH = "./src/main/java/database/user-data.xml";
    public static final String FLASHCARD_PATH = "./src/main/java/components/flashcard.fxml";

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

    public XMLReader xmlReader;
    public String userId;

    @FXML
    public void initialize()
    {
        setFlashcardBoxProperties();
        this.xmlReader = new XMLReader(USER_DATA_PATH, "user");
        this.userId = this.xmlReader.getTagTextContent(this.xmlReader.userDataRootNode, "_id");
        addFlashcards();
    }

    @FXML
    public void goToNotificationsScene(MouseEvent mouseEvent) throws IOException
    {
        switchScene(mouseEvent, NOTIFICATIONS_SCENE_PATH);
    }

    @FXML
    public void exitApplication(MouseEvent mouseEvent)
    {
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.close();
    }

    public void setFlashcardBoxProperties()
    {
        this.flashcardBox.setHgap(25);
        this.flashcardBox.setAlignment(Pos.TOP_CENTER);
    }

    public void addFlashcards()
    {
        try {
            Element userElement = (Element) this.xmlReader.userDataDocument.getElementsByTagName("user").item(0);
            Element flashcardsElements = (Element) userElement.getElementsByTagName("flashcards").item(0);
            int flashcardListLength = flashcardsElements.getElementsByTagName("flashcard").getLength();
            for (int i = 0; i < flashcardListLength; i++) {
                AnchorPane flashcard = FXMLLoader.load(Paths.get(FLASHCARD_PATH).toUri().toURL());
                flashcardBox.getChildren().add(flashcard);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
