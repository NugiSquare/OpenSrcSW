package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class indexer {

    public static void main(String[] args) {

    }

    @SuppressWarnings({"rawtypes", "unchecked", "nls"})
    public void indexing(String arg) throws IOException {

        //reading file
        File input = new File(arg);
        FileInputStream fis = new FileInputStream(input);
        Document document = Jsoup.parse(fis, null, "", Parser.xmlParser());

        //create hashmap
        HashMap<String, ArrayList<String>> indexhm = new HashMap<String, ArrayList<String>>();

        //read body content
        Elements entries = document.select("doc");
        for (Element entry : entries) {
            //System.out.println(entry.select("body").text());
            String[] indexhash = entry.select("body").text().split("#");
            for(String hash : indexhash) {
                String[] indexhashdetail = hash.split(":");
                ArrayList value = indexhm.get(indexhashdetail[0]);
                if (value == null) {
                    indexhm.put(indexhashdetail[0], new ArrayList<String>());
                }
                indexhm.get(indexhashdetail[0]).add(entry.id());
                indexhm.get(indexhashdetail[0]).add(tfidf(Integer.parseInt(indexhashdetail[1]), df(entries, indexhashdetail[0]), entries.size()));
            }

        }
        System.out.println(Collections.singletonList(indexhm));

        FileOutputStream fileStream = new FileOutputStream("index.post");

        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);

        objectOutputStream.writeObject(indexhm);
        objectOutputStream.close();

    }

    public static String tfidf(int tf, int df, int N) {

        Double result = Double.valueOf(tf) * Math.log(Double.valueOf(N)/Double.valueOf(df));
        result = Math.round(result * 100d) / 100d;
        return result.toString();
    }

    public static int df(Elements entries, String target) {
        int count = 0;
        for (Element entry : entries) {
            String[] indexhash = entry.select("body").text().split("#");
            for (String hash : indexhash) {
                String[] indexhashdetail = hash.split(":");
                if (target.equals(indexhashdetail[0])) {
                    count++;
                }
            }
        }
        return count;
    }

}
