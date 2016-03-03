package org.cbsa.api.conf;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLConfig {

    /**
     * @param args
     * @throws ParserConfigurationException
     * @throws XPathExpressionException
     */
    public static String readfromXml(String key)
            throws ParserConfigurationException, SAXException, IOException,
            XPathExpressionException {
        // TODO Auto-generated method stub
        String value = "";
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true);
        DocumentBuilder builder;
        Document doc = null;

        builder = factory.newDocumentBuilder();
        doc = builder.parse("../etc/conf.xml");

        // Create XPathFactory object
        XPathFactory xpathFactory = XPathFactory.newInstance();

        // Create XPath object
        XPath xpath = xpathFactory.newXPath();

        XPathExpression xPathExpression = xpath
                .compile("/configuration/property[name='" + key
                        + "']/value/text()");
        NodeList nodelist = (NodeList) xPathExpression.evaluate(doc,
                XPathConstants.NODESET);
        for (int i = 0; i < nodelist.getLength(); i++) {
            value = nodelist.item(0).getNodeValue();
        }
        return value;
    }

}
