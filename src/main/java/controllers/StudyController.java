package controllers;

import database.UserDataReader;
import javafx.scene.control.Label;
import utilities.Flashcard;

import java.util.ArrayList;

/*
* TODO: When I sort the index number of the flashcard, I must guarantee that it isn't
*  a repeated number
* TODO: The score must be updated in the study scene and in the home scene as well
* */

public class StudyController extends Controller
{
    public Label scoreValue;
    public Label flashcardDescription;
    public ArrayList<Flashcard> flashcards;

    public void initialize()
    {
        UserDataReader userDataReader = new UserDataReader();
        this.flashcards = userDataReader.getFlashcards();
        this.flashcardDescription.setText(sortFlashcard().description);
    }

    public Flashcard sortFlashcard()
    {
        int sortedIndex = sortIndex(this.flashcards.size() - 1);
        return this.flashcards.get(sortedIndex);
    }

    public int sortIndex(int max)
    {
        return (int) (Math.random() * (max + 1));
    }

    public void updateScore()
    {
        nextFlashcard();
    }

    public void nextFlashcard()
    {
        this.flashcardDescription.setText(sortFlashcard().description);
    }
}
