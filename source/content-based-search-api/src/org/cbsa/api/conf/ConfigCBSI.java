package org.cbsa.api.conf;

public class ConfigCBSI {

	private static final String DATASET_PATH = "/opt/dataset";
	private static final String TEMP_PATH = "/opt/tmp";
	private static final String WC_RESULT_PATH = TEMP_PATH + "/result_";

	private static final String PDFBOX_PATH = "/lib/pdfbox-2.0.0-RC3.jar";
	private static final String FONTBOX_PATH = "/lib/fontbox-2.0.0-RC3.jar";

	public static String getPdfboxPath() {
		return PDFBOX_PATH;
	}

	public static String getFontboxPath() {
		return FONTBOX_PATH;
	}

	public static String getDatasetPath() {
		return DATASET_PATH;
	}

	public static String getTempPath() {
		return TEMP_PATH;
	}

	public static String getWcResultPath() {
		return WC_RESULT_PATH;
	}

}
