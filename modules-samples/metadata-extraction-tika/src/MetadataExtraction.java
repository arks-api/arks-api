import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

public class MetadataExtraction {

    public static void main(final String[] args) throws IOException,
            TikaException, SAXException {

        File file = new File("/home/aditya/dataset/oca.pdf");

        Parser parser = new AutoDetectParser();
        BodyContentHandler handler = null;
        Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream(file);
        ParseContext context = new ParseContext();

        parser.parse(inputstream, handler, metadata, context);

        String[] metadataNames = metadata.names();

        // Metadata Properties
        // for (String name : metadataNames) {
        //
        // System.out.println(name);
        // }

        // Get specific metadata
        System.out.println(metadata.get(MetadataProperties.TITLE));
        System.out.println(metadata.get(MetadataProperties.AUTHOR));
        System.out.println(metadata.get(MetadataProperties.CREATOR));
        System.out.println(metadata.get(MetadataProperties.CONTENT_TYPE));
        System.out.println(metadata.get(MetadataProperties.ENCRYPTION));
    }
}