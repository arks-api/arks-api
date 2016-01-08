package org.cbsa.api.type;

public class KeywordDetails extends Keyword {

    private final String fileID;

    public KeywordDetails(String fileID, String frequency, String keyword) {
        super(keyword, frequency);

        this.fileID = fileID;
    }

    public String getFileID() {
        return fileID;
    }

}
