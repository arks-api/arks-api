import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;


public class PageByPageSearch 
{

	public List<Container> findpages(String path, String searchKeyword) throws IOException 
	{
		// TODO Auto-generated method stub
		
		int i; // page no.
		
		File file = new File(path);
		PDFParser parser = new PDFParser(new FileInputStream(file));
		parser.parse();
		
        COSDocument cosDoc = parser.getDocument();
        PDFTextStripper reader = new PDFTextStripper();
        PDDocument doc = new PDDocument(cosDoc);
        
        List<Container> list = new ArrayList<Container>();
        
		for(i=0;i<=doc.getNumberOfPages();i++)
		{
			reader.setStartPage(i);
			reader.setEndPage(i);
			
			if(reader.getText(doc).contains(searchKeyword))
			{
				Container container = new Container();
				container.setContent(reader.getText(doc));
				container.setFilepath(path);
				container.setPageno(i);
				list.add(container);
			}
			
		}
		
		return list;
		
	}
	
}
