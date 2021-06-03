package utilities;

import org.bson.types.ObjectId;

import java.util.ArrayList;

public class User
{
    private static User user;
    private static ObjectId id;
    private static String name;
    private static String email;
    private static String password;
    private static ArrayList<Flashcard> flashcards;
    private static ArrayList<Flashcard> receivedFlashcards;

    private User()
    {
        flashcards = new ArrayList<>();
        receivedFlashcards = new ArrayList<>();
    }

    public static User getInstance()
    {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public ObjectId getId()
    {
        return id;
    }

    public void setId(ObjectId id)
    {
        User.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        User.name = name;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        User.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        User.password = password;
    }

    public void addFlashcard(Flashcard flashcard)
    {
        User.flashcards.add(flashcard);
    }

    public ArrayList<Flashcard> getFlashcards()
    {
        return flashcards;
    }

    public void removeAllFlashcards()
    {
        flashcards = new ArrayList<>();
    }

    public ArrayList<Flashcard> getReceivedFlashcards()
    {
        return receivedFlashcards;
    }

    public void addReceivedFlashcards(Flashcard receivedFlashcard)
    {
        User.receivedFlashcards.add(receivedFlashcard);
    }
}
