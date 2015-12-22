package com.integrations;

public class BackgroundScanner {

    public BackgroundScanner() {

    }

    public static void main(String[] args) {

    	// HBase Code
        MetadataManager metadataManager = new MetadataManager();
        
        
        // Spark Code
        JavaWordCount count = new JavaWordCount();
        String[] param = { "input", "output" };
        count.runWordCount(param);
    }
}
