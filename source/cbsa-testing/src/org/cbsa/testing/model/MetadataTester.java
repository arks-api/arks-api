package org.cbsa.testing.model;

import java.util.ArrayList;
import java.util.List;

import org.cbsa.api.model.FileMetadata;
import org.cbsa.api.model.Keyword;
import org.cbsa.api.model.MetadataManager;

public class MetadataTester {

    public static void main(String[] args) {

        MetadataManager metadataManager = new MetadataManager();

        ArrayList<String> javaBookDomains = new ArrayList<String>();
        javaBookDomains.add("java");
        javaBookDomains.add("programming");
        javaBookDomains.add("coding");

        List<Keyword> javaBookKeywords = new ArrayList<Keyword>();
        javaBookKeywords.add(new Keyword("java", "25"));
        javaBookKeywords.add(new Keyword("jdk", "20"));
        javaBookKeywords.add(new Keyword("polymorphism", "10"));
        javaBookKeywords.add(new Keyword("overloading", "10"));
        javaBookKeywords.add(new Keyword("exceptions", "20"));
        javaBookKeywords.add(new Keyword("jvm", "12"));

        FileMetadata javaBookData = new FileMetadata("1",
                "java complete reference", "hdfs://user/aditya/documents",
                "50", "600", javaBookDomains, javaBookKeywords);

        metadataManager.addNewFileMetadata(javaBookData);
    }

}