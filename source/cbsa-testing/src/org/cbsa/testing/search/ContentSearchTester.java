package org.cbsa.testing.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cbsa.api.controller.search.PageByPageSearch;
import org.cbsa.api.model.SearchResult;

public class ContentSearchTester {

    public static void main(String[] args) throws IOException {

        PageByPageSearch pageByPageSearch = new PageByPageSearch();

        List<String> searchKeywordList = new ArrayList<String>();
        searchKeywordList.add("primary key");

        List<SearchResult> resultCardList;
        resultCardList = pageByPageSearch.findpages(
                "/opt/dataset/MySQL Tutorials Point.pdf", searchKeywordList, 0);

        System.out.println("got list");

        for (SearchResult result : resultCardList) {
            System.out.println(result.getPageNumber());
        }

    }
}
