package com.company;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

public class kuir {

    public static void main(String[] args) throws IOException, TransformerException, ParserConfigurationException {

        makeCollection makeC = new makeCollection();
        makeKeyword makeK = new makeKeyword();
        indexer idxer = new indexer();

        if(args[0].equals("-c")) {
            makeC.makingcollection(args[1]);
        }
        else if (args[0].equals("-k")) {
            makeK.makingkeyword(args[1]);
        }
        else if (args[0].equals("-i")) {
            idxer.indexing(args[1]);
        }
        else {
            System.out.println("Wrong. Try Again.");
        }

    }
}
