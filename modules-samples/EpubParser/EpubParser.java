import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.epub.EpubReader;

public class EpubParser 
{
	String htmltext = "";
	public String epubParse(InputStream fileIn) throws IOException
	{
		EpubReader epubReader = new EpubReader();
		Book book = epubReader.readEpub(fileIn);

		for (int i = 0; i < book.getSpine().size(); i++) 
		{
			InputStream is = book.getSpine().getSpineReferences().get(i).getResource().getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) 
			{
				sb.append(line + "\n");
			}

			htmltext = sb.toString();
			htmltext = htmltext.replaceAll("\\<.*?\\>", "");
		}
		return htmltext;
		
	}
}
