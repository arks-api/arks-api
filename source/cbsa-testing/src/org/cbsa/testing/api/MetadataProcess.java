package org.cbsa.testing.api;

import java.io.IOException;

import org.cbsa.api.bgmapr.WordCount;

public class MetadataProcess {

	public static void main(String[] args) throws ClassNotFoundException,
			IOException, InterruptedException {

		WordCount wc = new WordCount();
		wc.wordCountHBase();
	}

}