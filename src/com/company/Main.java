package com.company;

import org.jsoup.nodes.Document;
import org.w3c.dom.Element;

import org.jsoup.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws ParserConfigurationException, TransformerException, IOException {

        String[] documentlist =new String[]{"떡","라면","아이스크림","초밥","파스타"};

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        org.w3c.dom.Document doc = docBuilder.newDocument();

        // docs element
        Element docs = doc.createElement("docs");
        doc.appendChild(docs);

        for (int i = 0; i < documentlist .length; i++) {

            File input = new File("src/data/"+documentlist[i]+".html");
            Document document = Jsoup.parse(input, "UTF-8");

            // DocID element
            Element did = doc.createElement("doc");
            docs.appendChild(did);

            // attribute value type = id
            did.setAttribute("id", Integer.toString(i+1));

            // title element
            Element doctitle = doc.createElement("title");
            doctitle.appendChild(doc.createTextNode(document.title()));
            did.appendChild(doctitle);

            //movieDirector element
            Element docbody = doc.createElement("body");
            docbody.appendChild(doc.createTextNode(document.select("p").text()));
            did.appendChild(docbody);


        }

        // Writing XML
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new FileOutputStream(new File("src/data/result.xml")));

        transformer.transform(source, result);

    }
}
