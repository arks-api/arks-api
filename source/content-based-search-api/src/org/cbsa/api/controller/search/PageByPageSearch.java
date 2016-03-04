package org.cbsa.api.controller.search;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.cbsa.api.conf.ConfigCBSI;
import org.cbsa.api.model.PageResult;
import org.cbsa.api.model.SearchResult;

public class PageByPageSearch {

    private final Logger logger = Logger.getLogger(PageByPageSearch.class
            .getName());

    public PageByPageSearch() {
        logger.setLevel(Level.INFO);
    }

    public List<SearchResult> findpages(String path,
            List<String> searchKeywordList, int fileCounter) throws IOException {

        int i; // page no.
        boolean hasKeywords = false;

        PDDocument finalDocument = new PDDocument();
        List<PDPage> pageList = new ArrayList<PDPage>();

        File file = new File(path);
        PDFParser parser = new PDFParser(new RandomAccessBuffer(
                new FileInputStream(file)));
        parser.parse();

        COSDocument cosDoc = parser.getDocument();
        PDFTextStripper reader = new PDFTextStripper();
        PDDocument doc = new PDDocument(cosDoc);

        List<SearchResult> list = new ArrayList<SearchResult>();

        for (i = 0; i <= doc.getNumberOfPages(); i++) {
            reader.setStartPage(i);
            reader.setEndPage(i);
            hasKeywords = true;

            for (String keyword : searchKeywordList) {

                if (!reader.getText(doc).toLowerCase()
                        .contains(keyword.toLowerCase())) {
                    hasKeywords = false;
                    break;
                }
            }

            if (hasKeywords) {

                SearchResult result = new PageResult();
                result.setFileContent(reader.getText(doc));
                result.setFilePath(path);
                result.setPageNumber(i);
                list.add(result);
                pageList.add(doc.getPage(i));
            }

        }

        for (PDPage page : pageList) {
            finalDocument.addPage(page);
        }

        finalDocument
                .save(ConfigCBSI.getResultPdfPath() + fileCounter + ".pdf");
        finalDocument.close();
        logger.info("Result Saved");

        return list;

    }
}
