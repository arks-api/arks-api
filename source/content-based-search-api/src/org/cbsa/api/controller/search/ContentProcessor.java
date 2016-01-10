package org.cbsa.api.controller.search;

import java.util.List;

import org.cbsa.api.model.Paragraph;

public class ContentProcessor {

    private List<String> keywordsList = null;
    private List<String> filePathList = null;

    public ContentProcessor(List<String> keywordsList, List<String> filePathList) {

        this.keywordsList = keywordsList;
        this.filePathList = filePathList;
    }

    public List<Paragraph> searchForParagraph() {

        List<Paragraph> paragraphList = null;

        // search paragraphs in given fileList for matching keywords

        return paragraphList;
    }

}