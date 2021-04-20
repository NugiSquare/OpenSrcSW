package com.company;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class kuir {

    public static void main(String[] args) throws IOException, TransformerException, ParserConfigurationException, ClassNotFoundException {

        makeCollection makeC = new makeCollection();
        makeKeyword makeK = new makeKeyword();
        indexer idxer = new indexer();
        searcher srcher = new searcher();
        genSnippet snippeter = new genSnippet();

        if(args[0].equals("-c")) {
            makeC.makingcollection(args[1]);
        }
        else if (args[0].equals("-k")) {
            makeK.makingkeyword(args[1]);
        }
        else if (args[0].equals("-i")) {
            idxer.indexing(args[1]);
        }
        else if (args[0].equals("-s")) {
            if(args[2].equals("-q")) {
                srcher.searching(args[1], args[3]);
            }
            else {
                System.out.println("There's no option for -s");
            }
        }
        else if (args[0].equals("-f")) {
            if(args[2].equals("-q")) {
                snippeter.snippeting(args[1], args[3]);
            }
            else {
                System.out.println("There's no option for -s");
            }
        }
        else {
            System.out.println("Wrong. Try Again.");
        }

    }
}
