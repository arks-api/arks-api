package org.cbsa.testing.api;

import java.util.Scanner;

import org.cbsa.api.ContentBasedSearch;

public class CBSTest {

	public static void main(String[] args) {

		ContentBasedSearch contentBasedSearch = new ContentBasedSearch();
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter Query : ");
		String query = scanner.next();
		contentBasedSearch.searchForPages(query);
	}
}
