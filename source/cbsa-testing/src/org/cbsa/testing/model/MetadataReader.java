package org.cbsa.testing.model;

import java.io.IOException;
import java.util.List;

import org.cbsa.api.model.FileMetadata;
import org.cbsa.api.model.MetadataManager;

public class MetadataReader {

    public static void main(String[] args) throws IOException {

        MetadataManager metadataManager = new MetadataManager();
        List<FileMetadata> fileMetadataList = metadataManager.getFileMetadata();

        /*
         * for (FileMetadata fileMetadata : fileMetadataList) {
         * 
         * System.out.print(fileMetadata.getFileID() + " " +
         * fileMetadata.getFileName() + " " + fileMetadata.getFilePath() + " " +
         * fileMetadata.getFileSize() + "" + fileMetadata.getTotalPages() + " "
         * + fileMetadata.getFileDomain());
         * 
         * for (Keyword keyword : fileMetadata.getKeywords()) {
         * 
         * System.out.println(keyword.getKeyword() + " " +
         * keyword.getFrequency()); }
         * 
         * System.out.println(); System.out.println(); }
         */
    }

}
