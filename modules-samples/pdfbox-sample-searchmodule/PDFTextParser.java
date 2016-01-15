import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.pdfbox.cos.COSDocument;
import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.pdfparser.PDFParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.util.PDFTextStripper;

public class PDFTextParser 
{

		// Extract text from PDF Document
	static String pdftoText(String fileName,int pageno) throws IOException, CryptographyException 
	{
			
			File file = new File(fileName);
			
			
				PDFParser parser = new PDFParser(new FileInputStream(file));
				parser.parse();
				COSDocument cosDoc = parser.getDocument();
				PDFTextStripper pdfStripper = new PDFTextStripper();
				PDDocument pdDoc = new PDDocument(cosDoc);
				/*pdDoc.decrypt("");
				pdDoc.setAllSecurityToBeRemoved(true);*/
				pdfStripper.setStartPage(pageno);
				pdfStripper.setEndPage(pageno);
				String parsedText = pdfStripper.getText(pdDoc);
		
			
					if (cosDoc != null)
						cosDoc.close();
					if (pdDoc != null)
						pdDoc.close();
			
			return parsedText;
		}
	
		public static void main(String args[]) throws IOException, CryptographyException
		{
			for(int i = 0 ; i < 1000 ; i++)
			{
				String s = pdftoText("/home/rahul/Documents/MYPDF/HADOOP/Hadoop The Definitive Guide 2nd Edition.pdf",i);
				String keyword = "Java";
				if(s.contains(keyword))
				{
					System.out.println("PAGE NO "+i+" contains "+keyword);
				}
			}
		}
}