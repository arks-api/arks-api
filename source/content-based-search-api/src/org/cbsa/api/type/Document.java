package org.cbsa.api.type;

/***
 * Document class to hold document ID and DocumentName
 */
public class Document {

    private final String documentID;
    private final String documentName;

    public Document(String documentID, String documentName) {

        this.documentID = documentID;
        this.documentName = documentName;
    }

    public String getDocumentID() {
        return documentID;
    }

    public String getDocumentName() {
        return documentName;
    }

}