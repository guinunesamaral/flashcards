package controllers;

import database.UserDataReader;
import database.UserDataWriter;
import database.WriterOptions;
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
    @FXML
    public FlowPane flashcardWrapper;
    @FXML
    public AnchorPane flashcardAnchorPane;
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

    private UserDataReader userDataReader;
    private Element flashcard;
    private RotateTransition rotateTransition;

    @FXML
    public void initialize()
    {
        this.userDataReader = new UserDataReader();
        this.rotateTransition = new RotateTransition();
    }

    public void setFlashcardProperties(int flashcardIndex)
    {
        this.flashcardAnchorPane.getStyleClass().addAll("green-bg");
        this.flashcardAnchorPane.onMouseClickedProperty().set(mouseEvent -> setFlashcardBehaviorOnMouseClicked());
        setFlashcardComponentsProperties(flashcardIndex);
    }

    public void setFlashcardComponentsProperties(int flashcardIndex)
    {
        Element user = this.userDataReader.getRootElements();
        Element flashcards = this.userDataReader.getTagElements(user, "flashcards", 0);
        this.flashcard = this.userDataReader.getTagElements(flashcards, "flashcard", flashcardIndex);

        setTitleProperties(this.userDataReader.getTagTextContent(this.flashcard, "title"));
        setDescriptionProperties(this.userDataReader.getTagTextContent(this.flashcard, "description"));
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
        this.rotateTransition.setNode(flashcardAnchorPane);
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
        if (this.flashcardAnchorPane.getStyleClass().contains("green-bg")) {
            this.flashcardAnchorPane.getStyleClass().remove("green-bg");
            this.flashcardAnchorPane.getStyleClass().add("red-bg");
        } else {
            this.flashcardAnchorPane.getStyleClass().remove("red-bg");
            this.flashcardAnchorPane.getStyleClass().add("green-bg");
        }
    }

    public void removeFlashcard(MouseEvent mouseEvent)
    {
        UserDataWriter userDataWriter = new UserDataWriter(WriterOptions.USE_EXISTING_FILE);
        String flashcardId = this.userDataReader.getTagTextContent(this.flashcard, "_id");
        // This action removes the flashcard from the user-data file
        userDataWriter.removeFlashcard(flashcardId);
        switchScene(mouseEvent, HOME_SCENE);
    }

    public void shareFlashcard(MouseEvent mouseEvent)
    {
    }

    public void editFlashcard(MouseEvent mouseEvent)
    {
    }
}
