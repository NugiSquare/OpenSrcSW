package com.company;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.snu.ids.kkma.index.Keyword;
import org.snu.ids.kkma.index.KeywordExtractor;
import org.snu.ids.kkma.index.KeywordList;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import java.io.*;
import java.util.*;

public class searcher {

    public static void main(String[] args) {

    }

    public void searching(String postfileaddr, String querycontent) throws IOException, ClassNotFoundException {

        //arg1 = "./index.post" arg2 = "query"
        FileInputStream fileStream = new FileInputStream(postfileaddr);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileStream);

        Object object = objectInputStream.readObject();
        objectInputStream.close();

        HashMap<String, ArrayList<String>> hashMap = (HashMap) object;

        /*
        for( Object key : hashMap.keySet() ){
            System.out.println( String.format(key + " -> " + hashMap.get(key)) );
        }

         */

        //make keywordList from query
        KeywordExtractor ke = new KeywordExtractor();
        KeywordList kl = ke.extractKeyword(querycontent, true);
        /*
        for (int i = 0; i < kl.size(); i++) {
            Keyword kwrd = kl.get(i);
        }
         */

        //read collection.xml for doc title
        File file = new File(postfileaddr);
        File input = new File(file.getAbsoluteFile().getParent().replaceFirst(".$","") + "collection.xml");
        FileInputStream fis = new FileInputStream(input);
        Document document = Jsoup.parse(fis, null, "", Parser.xmlParser());

        //Elements Iterator
        Elements entries = document.select("doc");
        //Start calcSim
        HashMap<Integer, Double> resultMap = new HashMap<Integer, Double>();
        for (Element entry : entries) {
            resultMap.put(Integer.parseInt(entry.id()), 0.0);
        }

        //doing calcSim
        for (int i = 0; i < kl.size(); i++) {
            Keyword kwrd = kl.get(i);
            ArrayList<String> arrayList = hashMap.get(kwrd.getString());
            for(int j = 0; j < arrayList.size(); j++) {
                if(j % 2 == 0) {
                    Integer tmpkey = Integer.parseInt(arrayList.get(j));
                    if (resultMap.get(tmpkey) == null) {
                        String strtmp2 = arrayList.get(j+1);
                        Double dbltmp2 = Double.parseDouble(strtmp2);
                        resultMap.put(tmpkey, Math.round(dbltmp2*100)/100.0);
                    } else {
                        Double dbltmp1 = resultMap.get(tmpkey);
                        Double dbltmp2 = Double.parseDouble(arrayList.get(j+1));
                        resultMap.put(Integer.parseInt(arrayList.get(j)), Math.round((dbltmp1 + dbltmp2)*100)/100.0);
                    }

                }
            }
        }

        //sort by key
        Object[] mapkey = resultMap.keySet().toArray();
        Arrays.sort(mapkey);

        //sort by value
        resultMap = (HashMap<Integer, Double>) sortByComparator((Map<Integer, Double>) resultMap, false);

        /*
        //print key and values
        for( Object key : resultMap.keySet() ){
            System.out.println( String.format(key + " -> " + resultMap.get(key)) );
        }

         */

        //read and print title content
        int flag = 0;
        for( Integer key : resultMap.keySet() ){
            for (Element entry : entries) {
                if(key == Integer.parseInt(entry.id())) {
                    System.out.println(entry.select("title").text());
                    flag++;
                }
            }
            if(flag == 3) {
                break;
            }
        }
    }

    private static Map<Integer, Double> sortByComparator(Map<Integer, Double> unsortMap, final boolean order)
    {

        List<Entry<Integer, Double>> list = new LinkedList<Entry<Integer, Double>>(unsortMap.entrySet());

        // Sorting the list based on values
        Collections.sort(list, new Comparator<Entry<Integer, Double>>()
        {
            public int compare(Entry<Integer, Double> o1,
                               Entry<Integer, Double> o2)
            {
                if (order)
                {
                    return o1.getValue().compareTo(o2.getValue());
                }
                else
                {
                    return o2.getValue().compareTo(o1.getValue());

                }
            }
        });

        // Maintaining insertion order with the help of LinkedList
        Map<Integer, Double> sortedMap = new LinkedHashMap<Integer, Double>();
        for (Entry<Integer, Double> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }
}
