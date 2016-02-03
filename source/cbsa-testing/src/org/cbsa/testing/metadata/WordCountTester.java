package org.cbsa.testing.metadata;

import java.io.IOException;

import org.cbsa.api.bgmapr.WordCount;

public class WordCountTester {

	public static void main(String[] args) throws ClassNotFoundException,
			IOException, InterruptedException {

		WordCount wc = new WordCount();
		wc.wordCountHBase();
	}

}