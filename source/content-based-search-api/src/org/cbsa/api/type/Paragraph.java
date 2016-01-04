package org.cbsa.api.type;

/***
 * Paragraph class to hold Document ID and Paragraph in that document
 */
public class Paragraph {

    private final String paragraphContent;
    private final String documentID;

    public Paragraph(String paragraphContent, String documentID) {

        this.paragraphContent = paragraphContent;
        this.documentID = documentID;
    }

    public String getParagraphContent() {

        return paragraphContent;
    }

    public String getDocumentID() {

        return documentID;
    }
}