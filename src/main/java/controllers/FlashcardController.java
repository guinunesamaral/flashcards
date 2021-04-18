package controllers;

import database.XMLReader;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;
import org.w3c.dom.Element;

public class FlashcardController
{
    public static final String USER_DATA_PATH = "./src/main/java/database/user-data.xml";

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
    public XMLReader xmlReader;
    public static int index = 0;

    @FXML
    public void initialize()
    {
        this.xmlReader = new XMLReader(USER_DATA_PATH, "user");
        this.rotateTransition = new RotateTransition();
        setFlashcardProperties();
        setFlashcardComponentsProperties();
    }

    public void setFlashcardProperties()
    {
        this.flashcard.getStyleClass().addAll("green-bg");
        this.flashcard.onMouseClickedProperty().set(mouseEvent -> setFlashcardBehaviorOnMouseClicked());
    }

    public void setFlashcardComponentsProperties()
    {
        Element userElement = this.xmlReader.getUserRootElements();
        Element flashcardsElements = this.xmlReader.getTagElements(userElement, "flashcards", 0);
        Element flashcardElement = this.xmlReader.getTagElements(flashcardsElements, "flashcard", index);
        String flashcardTitle = this.xmlReader.getTagTextContent(flashcardElement, "title");
        String flashcardDescription = this.xmlReader.getTagTextContent(flashcardElement, "description");

        index += 1;

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
}
