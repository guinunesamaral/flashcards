package userdata;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import utilities.Flashcard;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class UserDataReader
{
    public static final String USER_DATA = "./src/main/java/userdata/user-data.xml";
    public Document userDataDocument;
    public Node userDataRootNode;
    public Element flashcards;
    private static String flashcardId;

    public UserDataReader()
    {
        File userDataXml = new File(USER_DATA);
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            this.userDataDocument = documentBuilder.parse(userDataXml);
            this.userDataDocument.getDocumentElement().normalize();

            this.userDataRootNode = this.userDataDocument.getElementsByTagName("user").item(0);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public Element getRootElements()
    {
        return (Element) this.userDataDocument.getElementsByTagName("user").item(0);
    }

    public String getTagTextContent(Node origin, String tagName)
    {
        return ((Element) origin).getElementsByTagName(tagName).item(0).getTextContent();
    }

    public Element getTagElements(String tagName, int index)
    {
        return (Element) this.userDataDocument.getElementsByTagName(tagName).item(index);
    }

    public Element findFlashcard(int index)
    {
        return (Element) this.flashcards.getElementsByTagName("_id").item(index);
    }
}
