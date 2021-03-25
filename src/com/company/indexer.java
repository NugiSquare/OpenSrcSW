package com.company;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

public class indexer {

    public static void main(String[] args) {

    }

    @SuppressWarnings({"rawtypes", "unchecked", "nls"})
    public void indexing(String arg) throws IOException {

        FileOutputStream fileStream = new FileOutputStream("src/data/movie.ser");
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileStream);

        HashMap MovieMap = new HashMap();

        objectOutputStream.writeObject(MovieMap);
        objectOutputStream.close();



    }
}
