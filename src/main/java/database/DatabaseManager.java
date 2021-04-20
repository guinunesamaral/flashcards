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

public class DatabaseManager
{
    // This variable establishes connection with mongodb database using user and password
    public static final MongoClient client = MongoClients.create("mongodb+srv://admin:ESlZYIXgEj5PJxGZ@cluster0.9zhdc.mongodb.net/flashcards-project?retryWrites=true&w=majority");

    // This is the actual database, who contains all collections
    public static final MongoDatabase database = client.getDatabase("flashcards-project");
    public static final MongoCollection<Document> usersCollection = database.getCollection("users");
    public static final MongoCollection<Document> flashcardsCollection = database.getCollection("flashcards");

    public void register()
    {

    }

    public boolean login(String email, String password)
    {
        Document userDocument = usersCollection.find(new Document("email", email)).first();

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
        UserDataWriter xmlWriter = new UserDataWriter(UserDataWriter.WriterOptions.CREATE_FILE);

        Element root = xmlWriter.createElementWithMultipleValues("user");
        Element id = xmlWriter.createElementWithSingleValue("_id", userId);
        Element name = xmlWriter.createElementWithSingleValue("name", userName);
        Element email = xmlWriter.createElementWithSingleValue("email", userEmail);
        Element password = xmlWriter.createElementWithSingleValue("password", userPassword);
        Element flashcards = xmlWriter.createElementWithMultipleValues("flashcards");

        flashcardIds.forEach(idString -> {
            var flashcardObjectId = new ObjectId(idString);
            Document flashcardDocument = flashcardsCollection.find(new Document("_id", flashcardObjectId)).first();

            assert flashcardDocument != null;
            String title = flashcardDocument.get("title").toString();
            String description = flashcardDocument.get("description").toString();

            Element flashcard = xmlWriter.createElementWithMultipleValues("flashcard");
            Element flashcardId = xmlWriter.createElementWithSingleValue("_id", idString);
            Element flashcardTitle = xmlWriter.createElementWithSingleValue("title", title);
            Element flashcardDescription = xmlWriter.createElementWithSingleValue("description", description);
            xmlWriter.appendChildrenToParent(flashcard, flashcardId, flashcardTitle, flashcardDescription);
            xmlWriter.appendChildToParent(flashcards, flashcard);
        });
        xmlWriter.appendChildrenToParent(root, id, name, email, password, flashcards);
        xmlWriter.appendRootToDocument(root);
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
