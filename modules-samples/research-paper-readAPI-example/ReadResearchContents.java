import in.codehub.document.Document;
import in.codehub.document.DocumentIterator;
import in.codehub.paperparser.Paper;
import in.codehub.paperparser.PaperParser;
import in.codehub.pdfreader.PdfReader;

import java.io.IOException;


public class ReadResearchContents 
{
	
	public static void main(String[] args) throws IOException {
		PdfReader pdfReader = PdfReader.getInstance();
		Document document = pdfReader.read("/home/rahul/MYGIT/ADI/be-project-workspace/doc/references/Context-based-Indexing-in-Search-Engines-using-Ontology.pdf");
		DocumentIterator documentIterator = document.iterator();
		while(documentIterator.hasNextParagraph())
		{
			System.out.println(documentIterator.nextParagraph().text());
		}
	}
}
