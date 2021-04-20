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

    public enum WriterOptions
    {
        CREATE_FILE, USE_EXISTING_FILE
    }

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

    public Element createElementWithSingleValue(String elementName, String elementValue)
    {
        Element child = this.newDocument.createElement(elementName);
        child.appendChild(this.newDocument.createTextNode(elementValue));
        return child;
    }

    public Element createElementWithMultipleValues(String elementName)
    {
        return this.newDocument.createElement(elementName);
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

    public void removeFlashcard(String flashcardId)
    {
        Element flashcards = (Element) this.existingDocument.getElementsByTagName("flashcards").item(0);
        Element flashcard = null;

        int numberOfFlashcards = flashcards.getElementsByTagName("flashcard").getLength();
        boolean found = false;
        int index = 0;

        while (!found && index < numberOfFlashcards) {
            flashcard = (Element) flashcards.getElementsByTagName("flashcard").item(index);
            String id = flashcard.getElementsByTagName("_id").item(0).getTextContent();
            if (id.equals(flashcardId)) {
                found = true;
            }
            index += 1;
        }
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
}
