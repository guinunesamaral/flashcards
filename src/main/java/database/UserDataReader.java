package database;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLReader
{
    public File userDataXml;
    public DocumentBuilderFactory documentBuilderFactory;
    public DocumentBuilder documentBuilder;
    public Document userDataDocument;
    public Node userDataRootNode;

    public XMLReader(String xmlFilePath, String rootNode)
    {
        this.userDataXml = new File(xmlFilePath);
        this.documentBuilderFactory = DocumentBuilderFactory.newInstance();
        try {
            this.documentBuilder= this.documentBuilderFactory.newDocumentBuilder();
            this.userDataDocument = this.documentBuilder.parse(this.userDataXml);
            this.userDataDocument.getDocumentElement().normalize();

            this.userDataRootNode = this.userDataDocument.getElementsByTagName(rootNode).item(0);
        } catch (ParserConfigurationException | SAXException | IOException e) {
            e.printStackTrace();
        }
    }

    public String getTagTextContent(Node origin, String tagName)
    {
        return ((Element) origin).getElementsByTagName(tagName).item(0).getTextContent();
    }

    public Element getRootElements()
    {
        return (Element) this.userDataDocument.getElementsByTagName("user").item(0);
    }

    public Element getTagElements(Node origin, String tagName, int index)
    {
        return (Element) ((Element) origin).getElementsByTagName(tagName).item(index);
    }
}
