package org.cbsa.testing.api;

import org.cbsa.api.controller.search.RelatedWordScanner;

public class RelatedWordTester {

    public static void main(String[] args) {

        System.out.println(RelatedWordScanner
                .getRelatedWords("java polymorphism"));
    }
}
