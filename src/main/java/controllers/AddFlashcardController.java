package controllers;

import database.Database;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.bson.types.ObjectId;
import utilities.Flashcard;
import utilities.User;

import java.util.ArrayList;

public class AddFlashcardController extends Controller
{
    public TextField frontInput;
    public TextArea backInput;

    public void addFlashcard(MouseEvent mouseEvent)
    {
        ArrayList<ObjectId> users = new ArrayList<>();
        users.add(User.getInstance().getId());

        Flashcard flashcard = new Flashcard(new ObjectId(),
                users,
                this.frontInput.getText(),
                this.backInput.getText());
        User.getInstance().addFlashcard(flashcard);
        Database.getInstance().addFlashcard(flashcard);
        closeCurrentScene(mouseEvent);
    }
}
