package org.cbsa.api;

import java.util.List;

import org.cbsa.api.type.Document;
import org.cbsa.api.type.Paragraph;

public class ContentBasedSearch {

    /***
     * Construct object of ContentBasedSearch class to access search methods
     */
    public ContentBasedSearch() {

    }

    /***
     * Search files in HDFS and get list of relevant documents
     *
     * @param keyword
     * @return {@link Document}
     */
    public List<Document> searchForFiles(String keyword) {

        List<Document> documentList = null;

        // Call API methods here

        return documentList;
    }

    /***
     * Search files in HDFS and get paragraphs relevant to that keywords
     *
     * @param keyword
     * @return {@link Paragraph}
     */
    public List<Paragraph> searchForParagraph(String keyword) {

        List<Paragraph> paragraphList = null;

        // Call API methods here

        return paragraphList;
    }

}