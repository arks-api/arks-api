package org.cbsa.testing.metadata;

import org.cbsa.api.bgmapr.DirectoryScanner;
import org.cbsa.api.conf.ConfigCBSI;

public class DirectoryScannerTester {

    public static void main(String[] args) {

        System.out.println(DirectoryScanner.getFileList(ConfigCBSI
                .getDatasetPath()));
    }
}
