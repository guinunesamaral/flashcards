package database;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.w3c.dom.Element;

import java.util.*;
import java.util.stream.Stream;

public class Database
{
    // The singleton object for the database class
    private static Database database;

    private final String mongoDBConnectionString = "mongodb+srv://admin:ESlZYIXgEj5PJxGZ@cluster0.9zhdc.mongodb.net/flashcards-project?retryWrites=true&w=majority";
    // This variable establishes connection with mongodb database using user and password
    private final MongoClient client = MongoClients.create(mongoDBConnectionString);
    // This is the actual database, who contains all collections
    private final MongoDatabase mongoDatabase = client.getDatabase("flashcards-project");
    private final MongoCollection<Document> usersCollection = mongoDatabase.getCollection("users");
    private final MongoCollection<Document> flashcardsCollection = mongoDatabase.getCollection("flashcards");

    private Database()
    {
    }

    public static Database getInstance()
    {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    public boolean login(String email, String password)
    {
        Document userDocument = this.usersCollection.find(new Document("email", email)).first();

        assert userDocument != null;
        String userId = userDocument.get("_id").toString();
        String userName = userDocument.get("name").toString();
        String userEmail = userDocument.get("email").toString();
        /*
         * The user's flashcards are stored here as an string array
         * After splitting the initial string, the initial and last elements of the array
         * will still have the brackets mark, and this is why the replaceAll method is used
         * */
        Stream<String> flashcardIds = Arrays.stream(userDocument.get("flashcards")
                .toString()
                .split(","))
                .map(flashcardId -> flashcardId.replaceAll("[\\[\\]]", "").trim());
        if (userEmail.equals(email)) {
            String userPassword = userDocument.get("password").toString();
            if (userPassword.equals(password)) {
                storeUserData(userId, userName, userEmail, userPassword, flashcardIds);
                return true;
            }
        }
        return false;
    }

    public void storeUserData(String userId, String userName, String userEmail, String userPassword, Stream<String> flashcardIds)
    {
        UserDataWriter userDataWriter = new UserDataWriter(WriterOptions.CREATE_FILE);

        Element root = userDataWriter.createElement("user");
        Element id = userDataWriter.createElement("_id", userId);
        Element name = userDataWriter.createElement("name", userName);
        Element email = userDataWriter.createElement("email", userEmail);
        Element password = userDataWriter.createElement("password", userPassword);
        Element flashcards = userDataWriter.createElement("flashcards");

        flashcardIds.forEach(idString -> {
            var flashcardObjectId = new ObjectId(idString);
            Document flashcardDocument = this.flashcardsCollection.find(new Document("_id", flashcardObjectId)).first();

            assert flashcardDocument != null;
            String title = flashcardDocument.get("title").toString();
            String description = flashcardDocument.get("description").toString();

            Element flashcard = userDataWriter.createElement("flashcard");
            Element flashcardId = userDataWriter.createElement("_id", idString);
            Element flashcardTitle = userDataWriter.createElement("title", title);
            Element flashcardDescription = userDataWriter.createElement("description", description);
            userDataWriter.appendChildrenToParent(flashcard, flashcardId, flashcardTitle, flashcardDescription);
            userDataWriter.appendChildToParent(flashcards, flashcard);
        });
        userDataWriter.appendChildrenToParent(root, id, name, email, password, flashcards);
        userDataWriter.appendRootToDocument(root);
    }

    public void register()
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
