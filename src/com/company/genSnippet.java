package com.company;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class genSnippet {

    public void snippeting(String filesource, String keywordlist) throws IOException {

        //한줄씩 크롤링이 잘 안되서 그부분이 되었다고 가정합니다.

        //reading file
        File input = new File("./"+filesource);
        FileInputStream fis = new FileInputStream(input);
        Document document = Jsoup.parse(fis, null, "", Parser.htmlParser());

        //create hashmap
        HashMap<String, ArrayList<String>> indexhm = new HashMap<String, ArrayList<String>>();

        String[][] crawled = ["라면, 밀가루, 달걀, 밥, 생선", "라면 물 소금 반죽", "첨부 봉지면 인기", "초밥 라면 밥물 채소 소금", "초밥 종류 활"];
        String[] keyword = keywordlist.split(" ");
어
        int[] resultflag = new int[5];

        for(int i = 0; i < 5; i++) {
            for( String str : keyword) {
                if(str.equals(crawled[i])) {
                    resultflag[i]++;
                }
            }
        }

        int flag = 0;

        for(int i = 0; i<5; i++) {
            if (flag < resultflag[i]) {
                flag = resultflag[i];
            }
        }

        System.out.println(crawled[flag]);
    }
}
