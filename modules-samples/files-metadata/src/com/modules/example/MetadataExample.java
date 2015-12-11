package com.modules.example;

import java.util.ArrayList;

import com.modules.metadata.FileMetadata;
import com.modules.metadata.MetadataManager;

public class MetadataExample {

    public static void main(String[] args) {

        MetadataManager metadataManager = new MetadataManager();

        FileMetadata javaBookData = new FileMetadata();
        javaBookData.setFileName("java complete reference");
        javaBookData.setFileSize(50);
        javaBookData.setTotalPages(600);

        ArrayList<String> javaBookDomains = new ArrayList<String>();
        javaBookDomains.add("java");
        javaBookDomains.add("programming");
        javaBookDomains.add("coding");

        ArrayList<String> javaBookKeywords = new ArrayList<String>();
        javaBookKeywords.add("java");
        javaBookKeywords.add("jdk");
        javaBookKeywords.add("polymorphism");
        javaBookKeywords.add("overloading");
        javaBookKeywords.add("exceptions");
        javaBookKeywords.add("jvm");

        javaBookData.setFileDomains(javaBookDomains);
        javaBookData.setKeywords(javaBookKeywords);

        metadataManager.addNewFileMetadata(javaBookData);
    }

}
