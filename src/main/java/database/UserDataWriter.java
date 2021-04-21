package database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

public class UserDataWriter
{
    public static final String USER_DATA_PATH = "./src/main/java/userdata/user-data.xml";

    public Document newDocument;
    public Document existingDocument;

    public UserDataWriter(WriterOptions option)
    {
        if (option == WriterOptions.CREATE_FILE) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder;
            try {
                docBuilder = dbf.newDocumentBuilder();
                this.newDocument = docBuilder.newDocument();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        } else if (option == WriterOptions.USE_EXISTING_FILE) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder builder = factory.newDocumentBuilder();
                this.existingDocument = builder.parse(USER_DATA_PATH);
            } catch (SAXException | IOException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

    public void appendRootToDocument(Element root)
    {
        try {
            this.newDocument.appendChild(root);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(this.newDocument);
            StreamResult result = new StreamResult(new File(USER_DATA_PATH));
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }

    public void appendChildrenToParent(Element parent, Element... children)
    {
        for (Element child : children) {
            parent.appendChild(child);
        }
    }

    public void appendChildToParent(Element parent, Element child)
    {
        parent.appendChild(child);
    }

    public Element createElement(String elementName)
    {
        return this.newDocument.createElement(elementName);
    }

    public Element createElementWithChild(String elementName, String elementValue)
    {
        Element child = this.newDocument.createElement(elementName);
        child.appendChild(this.newDocument.createTextNode(elementValue));
        return child;
    }

    public Element findFlashcard(Element flashcards, String flashcardId)
    {
        Element flashcard = null;

        int numberOfFlashcards = flashcards.getElementsByTagName("flashcard").getLength();
        boolean flashcardFound = false;
        int index = 0;

        while (!flashcardFound && index < numberOfFlashcards) {
            flashcard = (Element) flashcards.getElementsByTagName("flashcard").item(index);
            String id = flashcard.getElementsByTagName("_id").item(0).getTextContent();
            if (id.equals(flashcardId)) {
                flashcardFound = true;
            }
            index += 1;
        }
        return flashcard;
    }

    public void removeFlashcard(String flashcardId)
    {
        Element flashcards = (Element) this.existingDocument.getElementsByTagName("flashcards").item(0);
        Element flashcard = findFlashcard(flashcards, flashcardId);
        if (flashcard != null) {
            flashcards.removeChild(flashcard);
            try {
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                DOMSource source = new DOMSource(this.existingDocument);
                StreamResult result = new StreamResult(new File(USER_DATA_PATH));
                transformer.transform(source, result);
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateFlashcard(String flashcardId, String flashcardTitle, String flashcardDescription)
    {
        Element flashcards = (Element) this.existingDocument.getElementsByTagName("flashcards").item(0);
        Element flashcard = findFlashcard(flashcards, flashcardId);
        boolean flashcardChanged = false;

        if (!flashcardTitle.equals("")) {
            flashcard.getElementsByTagName("title").item(0).setTextContent(flashcardTitle);
            flashcardChanged = true;
        }
        if (!flashcardDescription.equals("")) {
            flashcard.getElementsByTagName("description").item(0).setTextContent(flashcardDescription);
            flashcardChanged = true;
        }
        if (flashcardChanged) {
            try {
                Transformer transformer = TransformerFactory.newInstance().newTransformer();
                DOMSource source = new DOMSource(this.existingDocument);
                StreamResult result = new StreamResult(new File(USER_DATA_PATH));
                transformer.transform(source, result);
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        }
    }
}
