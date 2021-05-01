package controllers;

import database.UserDataWriter;
import database.WriterOptions;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class UpdateFlashcard extends Controller
{
    public TextField titleInput;
    public TextArea descriptionInput;

    public void initialize()
    {
    }

    public void updateFlashcard(MouseEvent mouseEvent)
    {
        UserDataWriter userDataWriter = new UserDataWriter(WriterOptions.USE_EXISTING_FILE);
        userDataWriter.updateFlashcard(this.titleInput.getText(), this.descriptionInput.getText());
        switchScene(mouseEvent, HOME_SCENE);
    }

    public void returnToPreviousScene(MouseEvent mouseEvent)
    {
        switchScene(mouseEvent, HOME_SCENE);
    }
}
