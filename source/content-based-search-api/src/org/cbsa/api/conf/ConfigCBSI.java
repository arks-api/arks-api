package org.cbsa.api.conf;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;

import org.xml.sax.SAXException;

public class ConfigCBSI {

    private static final String DATASET_PATH = "dataset_path";
    private static final String TEMP_PATH = "temp_path";
    private static final String WC_RESULT_PATH = "wc_result_path";
    private static final String RESULT_PDF_PATH = "result_pdf_path";

    private static final String PDFBOX_PATH = "pdfbox_path";
    private static final String FONTBOX_PATH = "fontbox_path";

    private static final String STORAGE_MODE = "storage_mode";
    private static final String ONLINE_MODE = "online_mode";

    public static String getPdfboxPath() {

        try {

            return XMLConfig.readfromXml(PDFBOX_PATH);

        } catch (XPathExpressionException | ParserConfigurationException
                | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getFontboxPath() {

        try {

            return XMLConfig.readfromXml(FONTBOX_PATH);

        } catch (XPathExpressionException | ParserConfigurationException
                | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDatasetPath() {

        try {

            return XMLConfig.readfromXml(DATASET_PATH);

        } catch (XPathExpressionException | ParserConfigurationException
                | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTempPath() {

        try {

            return XMLConfig.readfromXml(TEMP_PATH);

        } catch (XPathExpressionException | ParserConfigurationException
                | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getWcResultPath() {

        try {

            return XMLConfig.readfromXml(WC_RESULT_PATH);

        } catch (XPathExpressionException | ParserConfigurationException
                | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getResultPdfPath() {

        try {

            return XMLConfig.readfromXml(RESULT_PDF_PATH);

        } catch (XPathExpressionException | ParserConfigurationException
                | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStorageMode() {

        try {

            return XMLConfig.readfromXml(STORAGE_MODE);

        } catch (XPathExpressionException | ParserConfigurationException
                | SAXException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean getOnlineMode() {

        try {

            if (XMLConfig.readfromXml(ONLINE_MODE).equals("true")) {
                return true;
            }

        } catch (XPathExpressionException | ParserConfigurationException
                | SAXException | IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
