package controllers;

import database.Database;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.bson.Document;
import utilities.User;

public class ShareFlashcardController extends Controller
{
    public Label shareFlashcardLabel;
    public TextField emailInput;
    public static int flashcardIndex;

    public static void setFlashcardIndex(int flashcardIndex)
    {
        ShareFlashcardController.flashcardIndex = flashcardIndex;
    }

    public void shareFlashcard(MouseEvent mouseEvent)
    {
        String emailProvided = this.emailInput.getText();

        if (!emailProvided.equals(User.getInstance().getEmail())) {
            Database database = Database.getInstance();
            Document addresseeDocument = database.usersCollection.find(new Document("email", emailProvided)).first();

            if (addresseeDocument != null) {
                this.shareFlashcardLabel.setText("Share flashcard with:");
                this.shareFlashcardLabel.getStyleClass().add("text-black");
                this.shareFlashcardLabel.getStyleClass().remove("text-red");

                database.shareFlashcard(flashcardIndex, emailProvided, addresseeDocument);
                closeCurrentScene(mouseEvent);
            }
            else {
                this.shareFlashcardLabel.setText("Enter a valid email");
                this.shareFlashcardLabel.getStyleClass().remove("text-black");
                this.shareFlashcardLabel.getStyleClass().add("text-red");
            }
        }
    }
}
