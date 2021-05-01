package controllers;

import database.UserDataWriter;
import database.WriterOptions;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.bson.types.ObjectId;
import org.w3c.dom.Element;

public class AddFlashcardController extends Controller
{
    public TextField titleInput;
    public TextArea descriptionInput;

    public void addFlashcard(MouseEvent mouseEvent)
    {
        UserDataWriter userDataWriter = new UserDataWriter(WriterOptions.USE_EXISTING_FILE);
        ObjectId objectId = new ObjectId();

        Element flashcard = userDataWriter.createElement("flashcard");
        Element flashcardId = userDataWriter.createElement("_id", objectId.toString());
        Element flashcardTitle = userDataWriter.createElement("title", titleInput.getText());
        Element flashcardDescription = userDataWriter.createElement("description", descriptionInput.getText());

        userDataWriter.addFlashcard(flashcard, flashcardId, flashcardTitle, flashcardDescription);

        Stage window = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        window.close();
    }
}
