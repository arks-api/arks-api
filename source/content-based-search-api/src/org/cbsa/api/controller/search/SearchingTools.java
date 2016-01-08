package org.cbsa.api.controller.search;

import java.util.List;

import org.cbsa.api.type.Paragraph;

public class SearchingTools {

    private ContentProcessor contentProcessor;

    public SearchingTools(List<String> keywordsList, List<String> filePathList) {

        contentProcessor = new ContentProcessor(keywordsList, filePathList);
    }

    public List<Paragraph> paragraphSearch(String keyword) {

        List<Paragraph> paragraphList = null;

        paragraphList = contentProcessor.searchForParagraph();

        return paragraphList;
    }
}
