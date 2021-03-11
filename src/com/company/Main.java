package com.company;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Main {

    public static void main(String[] args) throws ParserConfigurationException, TransformerException, FileNotFoundException {

        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

        Document doc = docBuilder.newDocument();

        // book element
        Element movie = doc.createElement("movie");
        doc.appendChild(movie);

        // code element
        Element code = doc.createElement("code");
        movie.appendChild(code);

        // attribute value type = SF
        code.setAttribute("type", "SF");

        // name element
        Element movieName = doc.createElement("movieName");
        movieName.appendChild(doc.createTextNode("테넷"));
        code.appendChild(movieName);

        //movieDirector element
        Element movieDirector = doc.createElement("movieDirector");
        movieDirector.appendChild(doc.createTextNode("크리스토퍼 놀란"));
        code.appendChild(movieDirector);

        // Writing XML
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new FileOutputStream(new File("src/data/book.xml")));

        transformer.transform(source, result);

    }
}
