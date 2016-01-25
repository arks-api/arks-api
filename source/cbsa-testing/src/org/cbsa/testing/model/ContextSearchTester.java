package org.cbsa.testing.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.cbsa.api.controller.search.PageByPageSearch;
import org.cbsa.api.model.ResultCard;

public class ContextSearchTester {

    public static void main(String[] args) throws IOException {

        PageByPageSearch pageByPageSearch = new PageByPageSearch();

        List<String> searchKeywordList = new ArrayList<String>();
        searchKeywordList.add("POSIX");

        List<ResultCard> resultCardList;
        resultCardList = pageByPageSearch.findpages(
                "/opt/dataset/Linux Kernel Development.pdf", searchKeywordList);

        System.out.println("got list");

        for (ResultCard card : resultCardList) {
            System.out.println(card.getPageNumber());
        }

    }
}
