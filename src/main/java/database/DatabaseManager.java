package database;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.Map;
import java.util.Objects;

public class DatabaseManager
{
    MongoClient client = MongoClients.create("mongodb+srv://admin:ESlZYIXgEj5PJxGZ@cluster0.9zhdc.mongodb.net/flashcards-project?retryWrites=true&w=majority");
    MongoDatabase database = client.getDatabase("flashcards-project");
    MongoCollection<Document> users = database.getCollection("users");
    MongoCollection<Document> flashcards = database.getCollection("flashcards");
    MongoCollection<Document> categories = database.getCollection("categories");

    public DatabaseManager()
    {
        ProcessBuilder pb = new ProcessBuilder();
        Map<String, String> env = pb.environment();
        env.put("MONGODB_USER", "");
        env.put("MONGODB_PWD", "");

        Document user = users.find(new Document("name", "Guilherme Nunes")).first();
        Document flashcard = flashcards.find(new Document("title", "First flashcard")).first();
        Document category = categories.find(new Document("name", "General")).first();

        System.out.println(Objects.requireNonNull(user).toJson());
        System.out.println(Objects.requireNonNull(flashcard).toJson());
        System.out.println(Objects.requireNonNull(category).toJson());
    }

    public void register()
    {

    }

    public void login()
    {

    }

    public void logout()
    {

    }

    public void createFlashcard()
    {

    }

    public void editFlashcard()
    {

    }

    public void receiveFlashcard()
    {

    }

    public void shareFlashcard()
    {

    }

    public void removeFlashcard()
    {

    }
}
