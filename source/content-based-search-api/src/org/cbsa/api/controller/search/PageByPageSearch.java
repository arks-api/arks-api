package org.cbsa.api.controller.search;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.io.RandomAccessBuffer;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.cbsa.api.model.PageCard;
import org.cbsa.api.model.ResultCard;

public class PageByPageSearch {

    public List<ResultCard> findpages(String path,
            List<String> searchKeywordList) throws IOException {

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

        List<ResultCard> list = new ArrayList<ResultCard>();

        for (i = 0; i <= doc.getNumberOfPages(); i++) {
            reader.setStartPage(i);
            reader.setEndPage(i);
            hasKeywords = true;

            for (String keyword : searchKeywordList) {

                if (!reader.getText(doc).contains(keyword)) {
                    hasKeywords = false;
                    break;
                }
            }

            if (hasKeywords) {

                ResultCard result = new PageCard();
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

        finalDocument.save("/opt/dataset/results/final.pdf");
        finalDocument.close();
        System.out.println("Saved");

        return list;

    }
}
