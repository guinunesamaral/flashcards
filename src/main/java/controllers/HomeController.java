package controllers;

import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class HomeController extends Controller
{
    @FXML
    public AnchorPane header;
    @FXML
    public Button notificationsBtn;
    @FXML
    public Button exitBtn;
    @FXML
    public FlowPane flashcardsBox;

    public String userId;

    @FXML
    public void initialize()
    {
        File userDataXml = new File("./src/main/java/database/user-data.xml");
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            org.w3c.dom.Document userData = builder.parse(userDataXml);
            userData.getDocumentElement().normalize();

            NodeList nodeList = userData.getElementsByTagName("user");
            this.userId = ((Element) nodeList.item(0)).getElementsByTagName("_id").item(0).getTextContent();
            addFlashcards();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public void addFlashcards()
    {
        flashcardsBox.setHgap(25);
        flashcardsBox.setAlignment(Pos.TOP_CENTER);

        File userDataXml = new File("./src/main/java/database/user-data.xml");
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            org.w3c.dom.Document userData = builder.parse(userDataXml);
            userData.getDocumentElement().normalize();

            NodeList nodeList = userData.getElementsByTagName("user");
            NodeList flashcardsList = ((Element) nodeList.item(0)).getElementsByTagName("flashcards").item(0).getChildNodes();
            int flashcardsListLength = flashcardsList.getLength();
            for (int ind = 0; ind < flashcardsListLength; ind++) {
                Element flashcardXml = (Element) flashcardsList.item(ind);
                String flashcardTitle = flashcardXml.getElementsByTagName("title").item(0).getTextContent();
                String flashcardDescription = flashcardXml.getElementsByTagName("description").item(0).getTextContent();
                FlowPane flashcard = setFlashcardInformation(flashcardTitle, flashcardDescription);
                flashcardsBox.getChildren().add(flashcard);
            }
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public FlowPane setFlashcardInformation(String flashcardTitle, String flashcardDescription)
    {
        FlowPane flashcard = new FlowPane();
        flashcard.setAlignment(Pos.TOP_CENTER);

        Label title = new Label(flashcardTitle);
        title.getStyleClass().add("flashcard__title");
        Label description = new Label(flashcardDescription);
        description.getStyleClass().add("flashcard__description");
        description.visibleProperty().set(false);

        ToolBar flashcardToolBar = new ToolBar();
        flashcardToolBar.getStyleClass().add("flashcardToolBar");
        Button removeBtn = new Button();
        removeBtn.getStyleClass().add("flashcard__remove-btn");
        Button shareBtn = new Button();
        shareBtn.getStyleClass().add("flashcard__share-btn");
        Button editBtn = new Button();
        editBtn.getStyleClass().add("flashcard__edit-btn");

        flashcardToolBar.getItems().add(removeBtn);
        flashcardToolBar.getItems().add(shareBtn);
        flashcardToolBar.getItems().add(editBtn);

        flashcard.getChildren().addAll(title, description, flashcardToolBar);

        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setDuration(Duration.millis(1000));
        rotateTransition.setAxis(Rotate.Y_AXIS);
        rotateTransition.setCycleCount(1);
        rotateTransition.setNode(flashcard);

        flashcard.getStyleClass().add("green-bg");
        flashcard.getStyleClass().add("flashcard");
        flashcard.onMouseClickedProperty().set(mouseEvent -> {
            rotateTransition.play();
            if (title.visibleProperty().get()) {
                title.visibleProperty().set(false);
                description.visibleProperty().set(true);
                flashcardToolBar.visibleProperty().set(false);
            } else {
                title.visibleProperty().set(true);
                description.visibleProperty().set(false);
                flashcardToolBar.visibleProperty().set(true);
            }
            if (flashcard.getStyleClass().contains("green-bg")) {
                flashcard.getStyleClass().remove("green-bg");
                flashcard.getStyleClass().add("red-bg");
            } else {
                flashcard.getStyleClass().remove("red-bg");
                flashcard.getStyleClass().add("green-bg");
            }
        });

        return flashcard;
    }

    @FXML
    public void goToNotificationsScene(MouseEvent mouseEvent) throws IOException
    {
        FXMLLoader notificationsFXML = new FXMLLoader(Paths.get("./src/main/java/fxml/notifications.fxml").toUri().toURL());
        switchScene(mouseEvent, notificationsFXML);
    }

    @FXML
    public void exitApplication(MouseEvent mouseEvent)
    {
        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.close();
    }
}
