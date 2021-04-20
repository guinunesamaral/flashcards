package controllers;

import database.UserDataReader;
import database.UserDataWriter;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import org.w3c.dom.Element;

public class FlashcardController extends Controller
{
    public static final String USER_DATA_PATH = "./src/main/java/userdata/user-data.xml";

    @FXML
    public FlowPane flashcardWrapper;
    @FXML
    public AnchorPane flashcard;
    @FXML
    public Label flashcardTitle;
    @FXML
    public Label flashcardDescription;
    @FXML
    public ToolBar flashcardToolBar;
    @FXML
    public Button removeFlashcardBtn;
    @FXML
    public Button shareFlashcardBtn;
    @FXML
    public Button editFlashcardBtn;

    public RotateTransition rotateTransition;
    public UserDataReader xmlReader;
    public UserDataWriter xmlWriter;
    public Element user;
    public Element flashcards;

    public String flashcardId;
    public int index;

    @FXML
    public void initialize()
    {
        this.xmlReader = new UserDataReader(USER_DATA_PATH, "user");
        this.rotateTransition = new RotateTransition();
        this.user = this.xmlReader.getRootElements();
        this.flashcards = this.xmlReader.getTagElements(this.user, "flashcards", 0);
    }

    public void setIndex(int index)
    {
        this.index = index;
    }

    public void setFlashcardProperties()
    {
        this.flashcard.getStyleClass().addAll("green-bg");
        this.flashcard.onMouseClickedProperty().set(mouseEvent -> setFlashcardBehaviorOnMouseClicked());
        setFlashcardComponentsProperties();
    }

    public void setFlashcardComponentsProperties()
    {
        System.out.println(this.index);
        Element flashcard = this.xmlReader.getTagElements(this.flashcards, "flashcard", this.index);
        this.flashcardId = this.xmlReader.getTagTextContent(flashcard, "_id");

        String flashcardTitle = this.xmlReader.getTagTextContent(flashcard, "title");
        String flashcardDescription = this.xmlReader.getTagTextContent(flashcard, "description");

        setTitleProperties(flashcardTitle);
        setDescriptionProperties(flashcardDescription);
        setRotateProperties();
    }

    public void setTitleProperties(String title)
    {
        this.flashcardTitle.setText(title);
    }

    public void setDescriptionProperties(String description)
    {
        this.flashcardDescription.setText(description);
        this.flashcardDescription.visibleProperty().set(false);
    }

    public void setRotateProperties()
    {
        this.rotateTransition.setDuration(Duration.millis(1000));
        this.rotateTransition.setAxis(Rotate.Y_AXIS);
        this.rotateTransition.setCycleCount(1);
        this.rotateTransition.setNode(flashcard);
    }

    public void setFlashcardBehaviorOnMouseClicked()
    {
        this.rotateTransition.play();
        if (this.flashcardTitle.visibleProperty().get()) {
            this.flashcardTitle.visibleProperty().set(false);
            this.flashcardDescription.visibleProperty().set(true);
            this.flashcardToolBar.visibleProperty().set(false);
        } else {
            this.flashcardTitle.visibleProperty().set(true);
            this.flashcardDescription.visibleProperty().set(false);
            this.flashcardToolBar.visibleProperty().set(true);
        }
        if (this.flashcard.getStyleClass().contains("green-bg")) {
            this.flashcard.getStyleClass().remove("green-bg");
            this.flashcard.getStyleClass().add("red-bg");
        } else {
            this.flashcard.getStyleClass().remove("red-bg");
            this.flashcard.getStyleClass().add("green-bg");
        }
    }

    public void removeFlashcard(MouseEvent mouseEvent)
    {
        this.xmlWriter = new UserDataWriter(UserDataWriter.WriterOptions.USE_EXISTING_FILE);
        // This action removes the flashcard from the user-data file
        this.xmlWriter.removeFlashcard(this.flashcardId);
        switchScene(mouseEvent, HOME_SCENE_PATH);
    }

    public void shareFlashcard(MouseEvent mouseEvent)
    {
    }

    public void editFlashcard(MouseEvent mouseEvent)
    {
    }
}
