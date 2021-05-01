package controllers;

import database.UserDataWriter;
import database.WriterOptions;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class UpdateFlashcardController extends Controller
{
    public TextField titleInput;
    public TextArea descriptionInput;

    public void initialize()
    {
    }

    public void updateFlashcard()
    {
        UserDataWriter userDataWriter = new UserDataWriter(WriterOptions.USE_EXISTING_FILE);
        userDataWriter.updateFlashcard(this.titleInput.getText(), this.descriptionInput.getText());
    }

}
