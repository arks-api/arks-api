package org.cbsa.api.controller.metadata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.cbsa.api.bgmapr.WordCount;
import org.cbsa.api.model.FileMetadata;
import org.cbsa.api.model.Keyword;
import org.cbsa.api.model.KeywordDetails;
import org.cbsa.api.model.MetaSchama;

@SuppressWarnings("deprecation")
public class MetadataManager {

    private final Logger logger = Logger.getLogger(WordCount.class.getName());

    private Configuration config;
    private HBaseAdmin admin;
    private HTable fileInfoTable;
    private HTable fileKeywordsTable;

    /***
     * Use this to create meta data tables and start connection to database to
     * store meta data of files. This constructor will create meta data tables
     * to HBase database if not exists and print error if fails to create
     * database tables.
     */
    public MetadataManager() {

        logger.setLevel(Level.INFO);

        if (!MetadataTables.createTables()) {

            logger.info("metadata table creation failed");
            return;
        }

        config = HBaseConfiguration.create();

        try {

            fileInfoTable = new HTable(config, MetaSchama.TB_FILE_INFO);
            fileKeywordsTable = new HTable(config, MetaSchama.TB_FILE_KEYWORDS);

        } catch (IOException e) {

            e.printStackTrace();
        }

        try {

            admin = new HBaseAdmin(config);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /***
     * Use this method to add new meta data of file to database.
     *
     * @param fileMetadata
     */
    public void addNewFileMetadata(FileMetadata fileMetadata) {

        Put putFileInfo = new Put(Bytes.toBytes(fileMetadata.getFileID()));

        putFileInfo.add(Bytes.toBytes(MetaSchama.CF_GENERAL),
                Bytes.toBytes(MetaSchama.CO_FILE_NAME),
                Bytes.toBytes(fileMetadata.getFileName()));
        putFileInfo.add(Bytes.toBytes(MetaSchama.CF_GENERAL),
                Bytes.toBytes(MetaSchama.CO_FILE_PATH),
                Bytes.toBytes(fileMetadata.getFilePath()));
        putFileInfo.add(Bytes.toBytes(MetaSchama.CF_GENERAL),
                Bytes.toBytes(MetaSchama.CO_FILE_SIZE),
                Bytes.toBytes(fileMetadata.getFileSize()));
        putFileInfo.add(Bytes.toBytes(MetaSchama.CF_GENERAL),
                Bytes.toBytes(MetaSchama.CO_TOTAL_PAGES),
                Bytes.toBytes(fileMetadata.getTotalPages()));
        putFileInfo.add(Bytes.toBytes(MetaSchama.CF_DOMAIN),
                Bytes.toBytes(MetaSchama.CO_SUB_DOMAIN),
                Bytes.toBytes(fileMetadata.getFileDomain()));
        putFileInfo.add(Bytes.toBytes(MetaSchama.CF_FILE_ID),
                Bytes.toBytes(MetaSchama.CO_ID),
                Bytes.toBytes(fileMetadata.getFileID()));

        List<Put> putKeywordsList = new ArrayList<Put>();

        List<Keyword> fileKeywords = fileMetadata.getKeywords();

        for (int i = 0; i < fileKeywords.size(); i++) {

            Put putKeywords = new Put(Bytes.toBytes(fileMetadata.getFileID()
                    + "_" + String.valueOf(i)));

            putKeywords.add(Bytes.toBytes(MetaSchama.CF_FILE_ID),
                    Bytes.toBytes(MetaSchama.CO_ID),
                    Bytes.toBytes(fileMetadata.getFileID()));
            putKeywords.add(Bytes.toBytes(MetaSchama.CF_KEYWORDS),
                    Bytes.toBytes(MetaSchama.CO_KEYWORD),
                    Bytes.toBytes(fileKeywords.get(i).getKeyword()));
            putKeywords.add(Bytes.toBytes(MetaSchama.CF_KEYWORDS),
                    Bytes.toBytes(MetaSchama.CO_FREQUENCY),
                    Bytes.toBytes(fileKeywords.get(i).getFrequency()));

            putKeywordsList.add(putKeywords);
        }

        try {

            fileInfoTable.put(putFileInfo);

            for (int i = 0; i < fileKeywords.size(); i++) {

                fileKeywordsTable.put(putKeywordsList.get(i));
            }

            fileInfoTable.close();
            fileKeywordsTable.close();

        } catch (IOException e) {

            e.printStackTrace();
            logger.info("file metadata insertion failed");
        }

        logger.info("file metadata insertion successful");

    }

    public List<FileMetadata> getFileMetadata() throws IOException {

        List<FileMetadata> fileMetadataList = null;
        Scan scanFileInfo = null;
        Scan scanFileKeywords = null;
        ResultScanner scannerFileInfo = null;
        ResultScanner scannerFileKeywords = null;
        boolean skip = true;

        scanFileInfo = new Scan();

        scanFileInfo.addFamily(Bytes.toBytes(MetaSchama.CF_GENERAL));
        scanFileInfo.addFamily(Bytes.toBytes(MetaSchama.CF_DOMAIN));

        scanFileKeywords = new Scan();
        scanFileKeywords.addFamily(Bytes.toBytes(MetaSchama.CF_KEYWORDS));

        try {

            scannerFileInfo = fileInfoTable.getScanner(scanFileInfo);
            scannerFileKeywords = fileKeywordsTable
                    .getScanner(scanFileKeywords);

        } catch (IOException e) {

            e.printStackTrace();
        }

        for (Result result = scannerFileInfo.next(); result != null; result = scannerFileInfo
                .next()) {

            for (KeyValue keyValue : result.list()) {

                // System.out.print(Bytes.toString(keyValue.getValue()) + " ");
            }

            System.out.println();
        }

        for (Result result = scannerFileKeywords.next(); result != null; result = scannerFileKeywords
                .next()) {

            for (KeyValue keyValue : result.list()) {

                System.out.println(Bytes.toString(keyValue.getValue()) + " ");
            }
        }

        scannerFileInfo.close();
        scannerFileKeywords.close();

        return fileMetadataList;
    }

    public List<String> getDocumentsWithKeywords(List<String> searchKeywordsList)
            throws IOException {

        List<String> documentPathList = new ArrayList<String>();
        List<KeywordDetails> keywordDetailsList = new ArrayList<KeywordDetails>();

        Scan scanFileInfo = null;
        Scan scanFileKeywords = null;
        ResultScanner scannerFileInfo = null;
        ResultScanner scannerFileKeywords = null;

        // retrieve keywords list

        scanFileKeywords = new Scan();
        scanFileKeywords.addFamily(Bytes.toBytes(MetaSchama.CF_FILE_ID));
        scanFileKeywords.addFamily(Bytes.toBytes(MetaSchama.CF_KEYWORDS));

        try {

            scannerFileKeywords = fileKeywordsTable
                    .getScanner(scanFileKeywords);

        } catch (IOException e) {

            e.printStackTrace();
        }

        for (Result result = scannerFileKeywords.next(); result != null; result = scannerFileKeywords
                .next()) {

            Iterator<KeyValue> iterator = result.list().iterator();

            while (iterator.hasNext()) {

                /*
                 * System.out.println(Bytes.toString(iterator.next().getValue())
                 * + " " + Bytes.toString(iterator.next().getValue()) + " " +
                 * Bytes.toString(iterator.next().getValue()));
                 */

                KeywordDetails tempKeywordDetails = new KeywordDetails(
                        Bytes.toString(iterator.next().getValue()),
                        Bytes.toString(iterator.next().getValue()),
                        Bytes.toString(iterator.next().getValue()));

                keywordDetailsList.add(tempKeywordDetails);

            }
        }

        // System.out.println();
        // System.out.println("Before Filtering");
        //
        // for (KeywordDetails keywordDetails : keywordDetailsList) {
        //
        // System.out.println(keywordDetails.getFileID() + " "
        // + keywordDetails.getFrequency() + " "
        // + keywordDetails.getKeyword());
        // }

        // filter the keywordDetails list as per searchKeywords

        Iterator<KeywordDetails> keywordDetailsIterator = keywordDetailsList
                .iterator();

        while (keywordDetailsIterator.hasNext()) {

            boolean keywordPresent = false;

            KeywordDetails tempKeywordDetails = keywordDetailsIterator.next();

            String currentkeyword = tempKeywordDetails.getKeyword();

            for (String keyword : searchKeywordsList) {

                if (keyword.equals(currentkeyword)) {

                    keywordPresent = true;
                    break;
                }
            }

            if (!keywordPresent) {

                keywordDetailsIterator.remove();
            }
        }

        // System.out.println("After Filtering");
        // for (KeywordDetails keywordDetails : keywordDetailsList) {
        //
        // System.out.println(keywordDetails.getFileID() + " "
        // + keywordDetails.getFrequency() + " "
        // + keywordDetails.getKeyword());
        // }

        // get fileID of selected keywords list

        Set<String> uniqueFileList = new HashSet<String>();

        for (KeywordDetails keywordDetails : keywordDetailsList) {

            uniqueFileList.add(keywordDetails.getFileID());
        }

        // for (String fileID : uniqueFileList) {
        //
        // System.out.println(uniqueFileList);
        // }

        // save paths of documents with those fileID

        scanFileInfo = new Scan();
        scanFileInfo.addFamily(Bytes.toBytes(MetaSchama.CF_FILE_ID));
        scanFileInfo.addColumn(Bytes.toBytes(MetaSchama.CF_GENERAL),
                Bytes.toBytes(MetaSchama.CO_FILE_PATH));

        try {

            scannerFileInfo = fileInfoTable.getScanner(scanFileInfo);

        } catch (IOException e) {

            e.printStackTrace();
        }

        for (Result result = scannerFileInfo.next(); result != null; result = scannerFileInfo
                .next()) {

            Iterator<KeyValue> iterator = result.list().iterator();

            while (iterator.hasNext()) {

                String fileID = Bytes.toString(iterator.next().getValue());
                String filePath = Bytes.toString(iterator.next().getValue());

                if (uniqueFileList.contains(fileID)) {

                    documentPathList.add(filePath);
                }

            }
        }

        scannerFileInfo.close();
        scannerFileKeywords.close();

        return documentPathList;
    }
}
