package database;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.bson.types.ObjectId;
import utilities.Flashcard;
import utilities.User;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
 * TODO: I must implement a regular expression to the email input, and also for the name, in
 *  the signUp method
 * TODO: It would be great to implement a system that sends a notification to the intended
 *  email of signUp, so I can verify if it's a real email
 * */

public class Database
{
    // The singleton object for the database class
    private static Database database;
    private final String mongoDBConnectionString = "mongodb+srv://admin:ESlZYIXgEj5PJxGZ@cluster0.9zhdc.mongodb.net/flashcards-project?retryWrites=true&w=majority";
    // This variable establishes connection with mongodb database using user and password
    private final MongoClient mongoClient = MongoClients.create(mongoDBConnectionString);
    // This is the actual database, who contains all collections
    private final MongoDatabase mongoDatabase = mongoClient.getDatabase("flashcards-project");
    public final MongoCollection<Document> usersCollection = mongoDatabase.getCollection("users");
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

    public boolean signIn(String email,
                          String password)
    {
        Document userDocument = this.usersCollection.find(new Document("email",
                email)).first();
        if (userDocument != null) {
            String userId = userDocument.get("_id").toString();
            String userName = userDocument.get("name").toString();
            String userEmail = userDocument.get("email").toString();

            List<Object> flashcards = userDocument.getList("flashcards",
                    Object.class);
            Stream<ObjectId> flashcardsIds = flashcards
                    .stream()
                    .filter(obj -> !obj.toString().equals(""))
                    .map(id -> new ObjectId(id.toString()));

            List<Object> received = userDocument.getList("received",
                    Object.class);
            Stream<ObjectId> receivedFlashcardsIds = received
                    .stream()
                    .filter(obj -> !obj.toString().equals(""))
                    .map(id -> new ObjectId(id.toString()));

            if (userEmail.equals(email)) {
                String userPassword = userDocument.get("password").toString();
                if (userPassword.equals(password)) {
                    storeUserData(new ObjectId(userId),
                            userName,
                            userEmail,
                            userPassword,
                            flashcardsIds,
                            receivedFlashcardsIds);
                    return true;
                }
            }
        }
        return false;
    }

    public boolean signUp(String name,
                          String email,
                          String password)
    {
        final String VALID_EMAIL = "^[a-zA-Z0-9_.-]+@[a-zA-Z0-9.-]+$";
        Pattern pattern = Pattern.compile(VALID_EMAIL);
        if (pattern.matcher(email).matches()) {
            this.usersCollection
                    .insertOne(new Document(Map.of(
                            "_id", new ObjectId(),
                            "name", name,
                            "email", email,
                            "password", password,
                            "flashcards", new ArrayList<ObjectId>(),
                            "received", new ArrayList<ObjectId>())));
            return true;
        }
        return false;
    }

    public void storeUserData(ObjectId userId,
                              String userName,
                              String userEmail,
                              String userPassword,
                              Stream<ObjectId> flashcardIds,
                              Stream<ObjectId> receivedFlashcardIds)
    {
        User user = User.getInstance();
        user.setId(userId);
        user.setName(userName);
        user.setEmail(userEmail);
        user.setPassword(userPassword);

        flashcardIds.forEach(id -> {
            Document flashcardDocument = this.flashcardsCollection.find(new Document("_id",
                    id)).first();

            if (flashcardDocument != null) {
                ArrayList<ObjectId> usersIds = getUsersIds(flashcardDocument);
                String front = flashcardDocument.get("front").toString();
                String back = flashcardDocument.get("back").toString();

                user.addFlashcard(new Flashcard(id,
                        usersIds,
                        front,
                        back));
            }
        });
        receivedFlashcardIds.forEach(id -> {
            Document flashcardDocument = this.flashcardsCollection.find(new Document("_id",
                    id)).first();

            if (flashcardDocument != null) {
                ArrayList<ObjectId> usersIds = getUsersIds(flashcardDocument);
                String front = flashcardDocument.get("front").toString();
                String back = flashcardDocument.get("back").toString();

                user.addReceivedFlashcards(new Flashcard(id,
                        usersIds,
                        front,
                        back));
            }
        });
    }

    private ArrayList<ObjectId> getUsersIds(Document flashcardDocument)
    {
        List<Object> usersIds = flashcardDocument
                .getList("users",
                        Object.class);
        return (ArrayList<ObjectId>) usersIds
                .stream()
                .filter(obj -> !obj.toString().equals(""))
                .map(id -> new ObjectId(id.toString()))
                .collect(Collectors.toList());
    }

    public void addFlashcard(Flashcard flashcard)
    {
        Document flashcardDocument = new Document(Map
                .of("_id",
                        flashcard.getId(),
                        "users",
                        flashcard.getUsers(),
                        "front",
                        flashcard.getFront(),
                        "back",
                        flashcard.getBack()));

        if (this.flashcardsCollection.find(new Document("_id",
                flashcard.getId())).first() == null) {
            this.flashcardsCollection.insertOne(flashcardDocument);
        }

        Document userDocument = this.usersCollection.find(new Document("email",
                User.getInstance().getEmail())).first();
        if (userDocument != null) {
            ArrayList<ObjectId> flashcardsIds = getFlashcardsIds(userDocument);
            if (!flashcardsIds.contains(flashcard.getId())) {
                flashcardsIds.add(flashcard.getId());
                this.usersCollection.updateOne(new Document("email",
                                User.getInstance().getEmail()),
                        new Document("$set",
                                new Document("flashcards",
                                        flashcardsIds)));
            }
        }
    }

    public void removeFlashcard(Flashcard flashcard)
    {
        String email = User.getInstance().getEmail();
        Document userDocument = this.usersCollection.find(new Document("email",
                email)).first();

        if (userDocument != null) {
            ArrayList<ObjectId> flashcardsIds = getFlashcardsIds(userDocument);
            if (flashcardsIds.contains(flashcard.getId())) {
                flashcardsIds.remove(flashcard.getId());
                this.usersCollection
                        .updateOne(new Document("email",
                                        email),
                                new Document("$set",
                                        new Document("flashcards",
                                                flashcardsIds)));
            }
            Document flashcardDocument = this.flashcardsCollection.find(new Document("_id",
                    flashcard.getId())).first();
            if (flashcardDocument != null) {
                ArrayList<ObjectId> usersIds = getUsersIds(flashcardDocument);
                if (usersIds.size() == 1) {
                    this.flashcardsCollection.deleteOne(new Document("_id",
                            flashcard.getId()));
                }
            }
        }
    }

    public void updateFlashcard(Flashcard flashcard)
    {
        this.flashcardsCollection.updateOne(new Document("_id", flashcard.getId()),
                new Document("$set", new Document("front", flashcard.getFront())));
        this.flashcardsCollection.updateOne(new Document("_id", flashcard.getId()),
                new Document("$set", new Document("back", flashcard.getBack())));
    }

    private ArrayList<ObjectId> getFlashcardsIds(Document document)
    {
        List<Object> flashcardsIds = document
                .getList("flashcards",
                        Object.class);
        return (ArrayList<ObjectId>) flashcardsIds
                .stream()
                .filter(obj -> !obj.toString().equals(""))
                .map(id -> new ObjectId(id.toString()))
                .collect(Collectors.toList());
    }

    public void shareFlashcard(int flashcardIndex,
                               String emailProvided,
                               Document addresseeDocument)
    {
        User user = User.getInstance();
        ArrayList<ObjectId> receivedFlashcardsIds = getReceivedFlashcards(addresseeDocument);
        Flashcard flashcard = user.getFlashcards().get(flashcardIndex);
        if (!receivedFlashcardsIds.contains(flashcard.getId())) {
            user.addReceivedFlashcards(new Flashcard(flashcard));
            receivedFlashcardsIds.add(flashcard.getId());
            this.usersCollection
                    .updateOne(new Document("email",
                                    emailProvided),
                            new Document("$set",
                                    new Document("received",
                                            receivedFlashcardsIds)));
        }
    }

    public ArrayList<ObjectId> getReceivedFlashcards(Document document)
    {
        List<Object> receivedFlashcardsIds = document
                .getList("received",
                        Object.class);
        return (ArrayList<ObjectId>) receivedFlashcardsIds
                .stream()
                .filter(obj -> !obj.toString().equals(""))
                .map(id -> new ObjectId(id.toString()))
                .collect(Collectors.toList());
    }

    public void receiveFlashcard()
    {

    }
}
