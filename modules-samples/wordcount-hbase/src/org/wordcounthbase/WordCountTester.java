package org.wordcounthbase;

import java.io.IOException;

public class WordCountTester {

    public static void main(String[] args) throws ClassNotFoundException,
            IOException, InterruptedException {

        WordCount wc = new WordCount();
        // contentSearchHadoop.searchPages();
        wc.wordCountHBase();
    }

}
