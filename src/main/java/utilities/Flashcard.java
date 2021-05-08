package utilities;

import org.bson.types.ObjectId;

import java.util.ArrayList;

public class Flashcard
{
    private final ObjectId id;
    private ArrayList<ObjectId> users;
    private String front;
    private String back;

    public Flashcard(Flashcard flashcard)
    {
        this.id = flashcard.getId();
        this.users = flashcard.getUsers();
        this.front = flashcard.getFront();
        this.back = flashcard.getBack();
    }

    public Flashcard(ObjectId id,
                     String front,
                     String back)
    {
        this.id = id;
        this.front = front;
        this.back = back;
    }

    public Flashcard(ObjectId id,
                     ArrayList<ObjectId> users,
                     String front,
                     String back)
    {
        this.id = id;
        this.users = users;
        this.front = front;
        this.back = back;
    }

    public ObjectId getId()
    {
        return this.id;
    }

    public ArrayList<ObjectId> getUsers()
    {
        return this.users;
    }

    public String getFront()
    {
        return front;
    }

    public void setFront(String front)
    {
        this.front = front;
    }

    public String getBack()
    {
        return back;
    }

    public void setBack(String back)
    {
        this.back = back;
    }
}
