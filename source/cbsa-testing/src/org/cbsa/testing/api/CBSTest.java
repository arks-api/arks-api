package org.cbsa.testing.api;

import org.cbsa.api.ContentBasedSearch;

public class CBSTest {

    public static void main(String[] args) {

        ContentBasedSearch contentBasedSearch = new ContentBasedSearch();
        contentBasedSearch.searchForPages("exception");
    }
}
