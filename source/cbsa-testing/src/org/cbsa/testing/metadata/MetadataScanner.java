package org.cbsa.testing.metadata;

import java.io.IOException;

import org.cbsa.api.controller.metadata.MetadataManager;

public class MetadataScanner {

    public static void main(String[] args) throws IOException {
        MetadataManager manager = new MetadataManager();
        manager.getFileMetadata();

    }

}
