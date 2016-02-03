package org.cbsa.testing.metadata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cbsa.api.controller.metadata.MetadataManager;

public class MetadataReader {

    public static void main(String[] args) throws IOException {

        MetadataManager metadataManager = new MetadataManager();

        List<String> keywordsList = new ArrayList<String>();
        keywordsList.add("blogging");
        // keywordsList.add("");

        List<String> result = metadataManager
                .getDocumentsWithKeywords(keywordsList);

        for (String filePath : result) {

            System.out.println(filePath);
        }

    }
}