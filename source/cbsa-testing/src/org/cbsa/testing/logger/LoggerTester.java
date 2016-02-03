package org.cbsa.testing.logger;

import java.util.logging.Level;
import java.util.logging.Logger;

public class LoggerTester {

	public static void main(String[] args) {

		Logger logger = Logger.getLogger(LoggerTester.class.getName());

		logger.setLevel(Level.INFO);
		logger.info("Test");
	}

}
