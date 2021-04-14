package database;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.*;
import java.util.stream.Stream;

public class DatabaseManager
{
    // This variable establishes connection with mongodb database using user and password
    public MongoClient client = MongoClients.create("mongodb+srv://admin:ESlZYIXgEj5PJxGZ@cluster0.9zhdc.mongodb.net/flashcards-project?retryWrites=true&w=majority");
    // This is the actual database, who contains all collections
    public MongoDatabase database = client.getDatabase("flashcards-project");
    public MongoCollection<Document> usersCollection = database.getCollection("users");
    public MongoCollection<Document> flashcardsCollection = database.getCollection("flashcards");

    public void register()
    {

    }

    public boolean login(String email, String password)
    {
        Document userDocument = Objects.requireNonNull(usersCollection.find(new Document("email", email)).first());
        String userId = userDocument.get("_id").toString();
        String userName = userDocument.get("name").toString();
        String userEmail = userDocument.get("email").toString();

        /*
        * The user's flashcards are stored here as an string array
        * After splitting the initial string, the initial and last elements of the array
        * will still have the brackets mark, and this is why the replaceAll method is used
        * */
        Stream<String> flashcardsIdsList = Arrays.stream(userDocument.get("flashcards")
                .toString()
                .split(","))
                .map(flashcardId -> flashcardId.replaceAll("[\\[\\]]", "").trim());

        if (userEmail.equals(email)) {
            String userPassword = userDocument.get("password").toString();
            if (userPassword.equals(password)) {
                storeUserData(userId, userName, userEmail, userPassword, flashcardsIdsList);
                return true;
            }
        }
        return false;
    }

    public void storeUserData(String userId, String userName, String userEmail, String userPassword, Stream<String> flashcardsIdsList)
    {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder docBuilder = dbf.newDocumentBuilder();
            org.w3c.dom.Document doc = docBuilder.newDocument();

            Element root = doc.createElement("user");
            doc.appendChild(root);

            Element idElement = doc.createElement("_id");
            idElement.appendChild(doc.createTextNode(userId));
            root.appendChild(idElement);

            Element nameElement = doc.createElement("name");
            nameElement.appendChild(doc.createTextNode(userName));
            root.appendChild(nameElement);

            Element emailElement = doc.createElement("email");
            emailElement.appendChild(doc.createTextNode(userEmail));
            root.appendChild(emailElement);

            Element passwordElement = doc.createElement("password");
            passwordElement.appendChild(doc.createTextNode(userPassword));
            root.appendChild(passwordElement);

            Element flashcardsElement = doc.createElement("flashcards");
            root.appendChild(flashcardsElement);

            flashcardsIdsList.forEach(flashcardId -> {
                var id = new ObjectId(flashcardId);
                Document flashcardDocument = flashcardsCollection.find(new Document("_id", id)).first();

                String flashcardTitle = Objects.requireNonNull(flashcardDocument).get("title").toString();
                String flashcardDescription = flashcardDocument.get("description").toString();

                Element flashcard = doc.createElement("flashcard");
                flashcardsElement.appendChild(flashcard);

                Element title = doc.createElement("title");
                title.appendChild(doc.createTextNode(flashcardTitle));
                flashcard.appendChild(title);

                Element description = doc.createElement("description");
                description.appendChild(doc.createTextNode(flashcardDescription));
                flashcard.appendChild(description);
            });

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            try {
                Transformer transformer = transformerFactory.newTransformer();
                DOMSource source = new DOMSource(doc);
                StreamResult result = new StreamResult(new File("./src/main/java/database/user-data.xml"));
                transformer.transform(source, result);
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
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
