package org.cbsa.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.cbsa.api.controller.metadata.MetadataManager;
import org.cbsa.api.controller.search.PageByPageSearch;
import org.cbsa.api.model.Document;
import org.cbsa.api.model.Paragraph;
import org.cbsa.api.model.SearchResult;

public class ContentBasedSearch {

    private final Logger logger = Logger.getLogger(ContentBasedSearch.class
            .getName());

    /***
     * Construct object of ContentBasedSearch class to access search methods
     */
    public ContentBasedSearch() {
        logger.setLevel(Level.INFO);
    }

    /***
     * Search files in HDFS and get list of relevant documents
     *
     * @param searchQuery
     * @return {@link Document}
     */
    public List<Document> searchForPages(String searchQuery) {

        List<Document> documentList = null;

        // Call API methods here
        MetadataManager metadataManager = new MetadataManager();

        List<String> keywordsList = new ArrayList<String>();
        StringTokenizer keywordTokenizer = new StringTokenizer(searchQuery);
        while (keywordTokenizer.hasMoreTokens()) {
            keywordsList.add(keywordTokenizer.nextToken());
        }

        List<String> result = null;
        try {
            result = metadataManager.getDocumentsWithKeywords(keywordsList);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PageByPageSearch pageByPageSearch = new PageByPageSearch();
        List<String> searchKeywordList = keywordsList;

        int counter = 0;
        List<SearchResult> searchResultList = null;
        try {

            for (String filePath : result) {

                logger.info(filePath);
                searchResultList = pageByPageSearch.findpages(filePath,
                        searchKeywordList, counter);
                counter++;
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

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