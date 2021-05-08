package userdata;

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

/*
* TODO: When I add or remove an item from the user-data file, it gets a messy.
*  I need to find a way to automatically format it
* */

public class UserDataWriter
{
    public static final String USER_DATA = "./src/main/java/userdata/user-data.xml";
    public Document userDataDocument;

    public UserDataWriter(WriterOptions option)
    {
        if (option == WriterOptions.CREATE_FILE) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder;
            try {
                docBuilder = dbf.newDocumentBuilder();
                this.userDataDocument = docBuilder.newDocument();
            } catch (ParserConfigurationException e) {
                e.printStackTrace();
            }
        } else if (option == WriterOptions.USE_EXISTING_FILE) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            try {
                DocumentBuilder builder = factory.newDocumentBuilder();
                this.userDataDocument = builder.parse(USER_DATA);
            } catch (SAXException | IOException | ParserConfigurationException e) {
                e.printStackTrace();
            }
        }
    }

    public void appendRootToDocument(Element root)
    {
        try {
            this.userDataDocument.appendChild(root);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(this.userDataDocument);
            StreamResult result = new StreamResult(new File(USER_DATA));
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
        return this.userDataDocument.createElement(elementName);
    }

    public Element createElement(String elementName, String elementValue)
    {
        Element child = this.userDataDocument.createElement(elementName);
        child.appendChild(this.userDataDocument.createTextNode(elementValue));
        return child;
    }

    public void addFlashcard(Element flashcard, Element id, Element title, Element description)
    {
        appendChildrenToParent(flashcard, id, title, description);
        this.userDataDocument.getElementsByTagName("flashcards").item(0).appendChild(flashcard);
        updateUserDataFile();
    }

    public void removeFlashcard(int flashcardIndex)
    {
        UserDataReader userDataReader = new UserDataReader();
        Element flashcard = userDataReader.findFlashcard(flashcardIndex);
        if (flashcard != null) {
            userDataReader.flashcards.removeChild(flashcard);
            updateUserDataFile();
        }
    }

//    public void updateFlashcard(String flashcardId, String flashcardFront, String flashcardBack)
//    {
//        UserDataReader userDataReader = new UserDataReader();
//        Element flashcard = userDataReader.findFlashcard(flashcardIndex);
//        boolean flashcardChanged = false;
//
//        if (flashcard != null) {
//            if (!flashcardFront.equals("")) {
//                flashcard.getElementsByTagName("front").item(0).setTextContent(flashcardFront);
//                flashcardChanged = true;
//            }
//            if (!flashcardBack.equals("")) {
//                flashcard.getElementsByTagName("back").item(0).setTextContent(flashcardBack);
//                flashcardChanged = true;
//            }
//        }
//        if (flashcardChanged) {
//            updateUserDataFile();
//        }
//    }

    private void updateUserDataFile()
    {
        try {
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            DOMSource source = new DOMSource(this.userDataDocument);
            StreamResult result = new StreamResult(new File(USER_DATA));
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
