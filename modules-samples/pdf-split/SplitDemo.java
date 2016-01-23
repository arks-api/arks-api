import org.apache.pdfbox.util.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.exceptions.COSVisitorException;
import java.io.IOException;
import java.util.List;
import java.util.Iterator;
/**
 *
 * @author Stephen H
 * Created 21 June 2014
 * Revised 25 June 2014
 * Email steve@printmyfolders.com
 * 
 * An example showing how to split a PDF file.
 * Here is how it works
 * 1. Load a PDF file. Make provisions to catch or throw IOException.
 * 2. Create an object of Splitter
 * 3. Use the split method to split the document
 * NOTE: This will create a PDF document out of each page and return them as a list
 * 4. The split method returns the PDFs as a list
 * 5. Create an iterator to iterate through them
 * 6. Do whatever you want with each file but catch COSVisitorException
 * In my case I am naming them according to the page number which I assume to start with 1 and saving them.
 * 7. If you get a COSVisitorException error display it with the number of the page where it occurred.
 */

public class SplitDemo {
    public static void main(String[] args) throws IOException {
        // Load the PDF. The PDDocument throws IOException
        PDDocument document = new PDDocument();
        document = PDDocument.load("C:\\Main.pdf");
        
        // Create a Splitter object
        Splitter splitter = new Splitter();
        
        // We need this as split method returns a list
        List<PDDocument> listOfSplitPages;
        
        // We are receiving the split pages as a list of PDFs
        listOfSplitPages = splitter.split(document);
        
        // We need an iterator to iterate through them
        Iterator<PDDocument> iterator = listOfSplitPages.listIterator();
        
        // I am using variable i to denote page numbers. 
        int i = 1;
        while(iterator.hasNext()){
            PDDocument pd = iterator.next();
            try{
                // Saving each page with its assumed page no.
                pd.save("C:\\Page " + i++ + ".pdf");
            } catch (COSVisitorException anException){
                // Something went wrong with a PDF object
                System.out.println("Something went wrong with page " + (i-1) + "\n Here is the error message" + anException);                
            }            
        }        
    }    
}
