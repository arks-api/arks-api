package com.modules.example;

import java.util.ArrayList;
import java.util.List;

import com.modules.metadata.FileMetadata;
import com.modules.metadata.Keyword;
import com.modules.metadata.MetadataManager;

public class MetadataExample {

    public static void main(String[] args) {

        MetadataManager metadataManager = new MetadataManager();

        FileMetadata javaBookData = new FileMetadata();
        javaBookData.setFileName("java complete reference");
        javaBookData.setFileSize("50");
        javaBookData.setTotalPages("600");

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

        javaBookData.setFileDomains(javaBookDomains);
        javaBookData.setKeywords(javaBookKeywords);

        metadataManager.addNewFileMetadata(javaBookData);
    }

}
