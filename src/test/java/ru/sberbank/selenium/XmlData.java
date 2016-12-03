package ru.sberbank.selenium;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class XmlData {

    private transient Collection data = null;

    public XmlData(final File xml) throws IOException {
        this.data = loadFromFile(xml);
    }

    private Collection loadFromFile(File xml) {

        List node = new ArrayList();

        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(xml);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("test");
            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node nNode = nList.item(temp);
                List nodeData = new ArrayList();
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    nodeData.add(eElement.getElementsByTagName("amount").item(0).getTextContent());
                    nodeData.add(eElement.getElementsByTagName("converterFrom").item(0).getTextContent());
                    nodeData.add(eElement.getElementsByTagName("converterTo").item(0).getTextContent());
                    nodeData.add(eElement.getElementsByTagName("source").item(0).getTextContent());
                    nodeData.add(eElement.getElementsByTagName("destination").item(0).getTextContent());
                    nodeData.add(eElement.getElementsByTagName("date").item(0).getTextContent());
                }
                node.add(nodeData.toArray());
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return node;
    }

    public Collection getData() {
        return data;
    }
}
