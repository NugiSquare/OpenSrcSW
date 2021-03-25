package com.company;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Comparator;

public class makeKeyword {

    public static void main(String[] args) {

    }

    public void makingkeyword(String arg) throws ParserConfigurationException, TransformerException, IOException {

        //copy collection.xml to index.xml
        String oriFilePath = arg;
        //복사될 파일경로
        String copyFilePath = "./index.xml";

        //파일객체생성
        File oriFile = new File(oriFilePath);
        //복사파일객체생성
        File copyFile = new File(copyFilePath);

        try {

            FileInputStream fis = new FileInputStream(oriFile); //읽을파일
            FileOutputStream fos = new FileOutputStream(copyFile); //복사할파일

            int fileByte = 0;
            // fis.read()가 -1 이면 파일을 다 읽은것
            while((fileByte = fis.read()) != -1) {
                fos.write(fileByte);
            }
            //자원사용종료
            fis.close();
            fos.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        //reading file
        File input = new File("./index.xml");
        FileInputStream fis = new FileInputStream(input);
        Document document = Jsoup.parse(fis, null, "", Parser.xmlParser());

        //read body content
        Elements entries = document.select("doc");
        for (Element entry : entries) {
            Element tag = entry.getAllElements().last();
            for (Node child : tag.childNodes()) {
                if (child instanceof TextNode && !((TextNode) child).isBlank()) {
                    KeywordExtractor ke = new KeywordExtractor();
                    KeywordList kl = ke.extractKeyword(child.toString(), true);
                    kl.sort(new Comparator<Keyword>() {
                        @Override
                        public int compare(Keyword o1, Keyword o2) {
                            if (o1.getCnt() < o2.getCnt()) {
                                return 1;
                            } else if (o1.getCnt() > o2.getCnt()) {
                                return -1;
                            }
                            return 0;
                        }
                    });

                    String replacement = "";

                    for (int i = 0; i < kl.size(); i++) {
                        Keyword kwrd = kl.get(i);
                        replacement = replacement + kwrd.getString() + ":" + kwrd.getCnt() + "#";
                    }
                    ((TextNode) child).text(replacement); //replace to word
                }
            }
        }

        // Writing XML
        java.io.FileWriter fw = new java.io.FileWriter("index.xml");
        fw.write(String.valueOf(document));
        fw.close();

    }
}
