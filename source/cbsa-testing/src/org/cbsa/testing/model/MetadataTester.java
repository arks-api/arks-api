package org.cbsa.testing.model;

import java.util.ArrayList;
import java.util.List;

import org.cbsa.api.model.FileMetadata;
import org.cbsa.api.model.Keyword;
import org.cbsa.api.model.MetadataManager;

public class MetadataTester {

    public static void main(String[] args) {

        MetadataManager metadataManager = new MetadataManager();

        List<Keyword> javaBookKeywords = new ArrayList<Keyword>();
        javaBookKeywords.add(new Keyword("java", "25"));
        javaBookKeywords.add(new Keyword("jdk", "20"));
        javaBookKeywords.add(new Keyword("polymorphism", "10"));
        javaBookKeywords.add(new Keyword("overloading", "10"));
        javaBookKeywords.add(new Keyword("exceptions", "20"));
        javaBookKeywords.add(new Keyword("jvm", "12"));

        FileMetadata javaBookData = new FileMetadata("1",
                "java complete reference",
                "hdfs://user/aditya/documents/java.pdf", "50", "600",
                "programming", javaBookKeywords);

        metadataManager.addNewFileMetadata(javaBookData);

        List<Keyword> cppBookKeywords = new ArrayList<Keyword>();
        cppBookKeywords.add(new Keyword("cpp", "25"));
        cppBookKeywords.add(new Keyword("virtual", "20"));
        cppBookKeywords.add(new Keyword("polymorphism", "10"));
        cppBookKeywords.add(new Keyword("overloading", "10"));
        cppBookKeywords.add(new Keyword("exceptions", "20"));
        cppBookKeywords.add(new Keyword("distructor", "12"));

        FileMetadata cppBookData = new FileMetadata("2",
                "cpp complete reference",
                "hdfs://user/aditya/documents/cpp.pdf", "50", "600",
                "programming", cppBookKeywords);

        metadataManager.addNewFileMetadata(cppBookData);

    }

}