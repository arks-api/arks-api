package org.cbsa.api.controller.bgprocess;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.cbsa.api.controller.metadata.MetadataManager;
import org.cbsa.api.controller.metadata.Stopwords;
import org.cbsa.api.model.FileMetadata;
import org.cbsa.api.model.Keyword;

/**
 * Java program to find count of repeated words in a file.
 *
 * @author
 */
public class WordCount extends Thread {

    public FileWriter fw;
    public String path;

    public WordCount(String path) {
        // TODO Auto-generated constructor stub
        this.path = path;
    }

    @Override
    public void run() {
        Set<String> set = new HashSet<String>();
        for (int i = 0; i < Stopwords.STOPWORDS1.length; i++) {
            set.add(Stopwords.STOPWORDS1[i]);
        }
        for (int i = 0; i < Stopwords.STOPWORDS2.length; i++) {
            set.add(Stopwords.STOPWORDS2[i]);
        }
        Map<String, Integer> wordMap = buildWordMap(path);
        List<Entry<String, Integer>> list = sortByValueInDecreasingOrder(wordMap);
        List<Keyword> l = new ArrayList<Keyword>();
        // System.out.println("List of repeated word from file and their count");
        for (Map.Entry<String, Integer> entry : list) {
            if (!set.contains(entry.getKey())) {
                // System.out.println(entry.getKey() + " => " +
                // entry.getValue());
                Keyword keyword = new Keyword(entry.getKey(), entry.getValue()
                        .toString());
                l.add(keyword);
            }
        }

        FileMetadata fileMetadata;
        try {

            fileMetadata = new FileMetadata(FileCounterManager.getCounter()
                    .toString(), path, path, "25MB", "1", "NO DOMAIN FOUND", l);
            MetadataManager mm = new MetadataManager();
            mm.addNewFileMetadata(fileMetadata);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public static Map<String, Integer> buildWordMap(String fileName) {
        // Using diamond operator for clean code
        Map<String, Integer> wordMap = new HashMap<>();
        // Using try-with-resource statement for automatic resource management
        try (FileInputStream fis = new FileInputStream(fileName);
                DataInputStream dis = new DataInputStream(fis);
                BufferedReader br = new BufferedReader(new InputStreamReader(
                        dis))) {
            // words are separated by whitespace

            String line = null;
            while ((line = br.readLine()) != null) {
                // do this if case sensitivity is not required i.e. Java = java
                line = line.replaceAll("\\p{Punct}|\\d", "").toLowerCase();
                String[] words = line.split(" ");
                for (String word : words) {
                    if (wordMap.containsKey(word)) {
                        wordMap.put(word, (wordMap.get(word) + 1));
                    } else {
                        wordMap.put(word, 1);
                    }
                }
            }
        } catch (IOException ioex) {
            ioex.printStackTrace();
        }
        return wordMap;
    }

    public static List<Entry<String, Integer>> sortByValueInDecreasingOrder(
            Map<String, Integer> wordMap) {
        Set<Entry<String, Integer>> entries = wordMap.entrySet();
        List<Entry<String, Integer>> list = new ArrayList<>(entries);
        Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1,
                    Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });
        return list;
    }
}
