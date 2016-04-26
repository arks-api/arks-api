package org.cbsa.api.controller.search;

import in.codehub.document.Document;
import in.codehub.paperparser.Paper;
import in.codehub.paperparser.PaperParser;
import in.codehub.pdfreader.PdfReader;

import java.io.File;
import java.io.IOException;

public class ResearchPaperParser {

    public ResearchPaperParser() {

    }

    public void parseResearchPaper() {

        Document document = null;

        try {

            document = PdfReader.getInstance().read(
                    new File("/opt/dataset/cbsaa.pdf"));

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Paper paper = PaperParser.getInstance().parse(document);

        System.out.println(paper.getTitle());
        System.out.println(paper.getAuthors().toString());
        System.out.println(paper.getAbstract());
        System.out.println(paper.getKeywords().toString());
        System.out.println(paper.getHeading().toString());
        System.out.println(paper.getReferences().toString());
    }
}
