package controllers;

import database.UserDataWriter;
import database.WriterOptions;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class UpdateFlashcard extends Controller
{
    @FXML
    public TextField titleInput;
    @FXML
    public TextArea descriptionInput;

    private String flashcardId;

    @FXML
    public void initialize()
    {
    }

    public void setFlashcardId(String flashcardId)
    {
        this.flashcardId = flashcardId;
    }

    @FXML
    public void updateFlashcard(MouseEvent mouseEvent)
    {
        UserDataWriter userDataWriter = new UserDataWriter(WriterOptions.USE_EXISTING_FILE);
        userDataWriter.updateFlashcard(this.flashcardId, this.titleInput.getText(), this.descriptionInput.getText());
        switchScene(mouseEvent, HOME_SCENE);
    }

    public void returnToPreviousScene(MouseEvent mouseEvent)
    {
        switchScene(mouseEvent, HOME_SCENE);
    }
}
