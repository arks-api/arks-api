package org.cbsa.api.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;

@SuppressWarnings("deprecation")
public class MetadataManager {

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

        if (!MetadataTables.createTables()) {

            System.err.println("Metadata Table Creation Failed");
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
            System.err.println("File Metadata Insertion Failed");
        }

        System.out.println("New File Metadata Added");

    }

    public List<FileMetadata> getFileMetadata() throws IOException {

        List<FileMetadata> fileMetadataList = null;
        Scan scanFileInfo = null;
        Scan scanFileKeywords = null;
        ResultScanner scannerFileInfo = null;

        scanFileInfo = new Scan();

        scanFileInfo.addColumn(Bytes.toBytes(MetaSchama.CF_FILE_ID),
                Bytes.toBytes(MetaSchama.CO_ID));
        scanFileInfo.addColumn(Bytes.toBytes(MetaSchama.CF_GENERAL),
                Bytes.toBytes(MetaSchama.CO_FILE_NAME));
        scanFileInfo.addColumn(Bytes.toBytes(MetaSchama.CF_GENERAL),
                Bytes.toBytes(MetaSchama.CO_FILE_PATH));
        scanFileInfo.addColumn(Bytes.toBytes(MetaSchama.CF_GENERAL),
                Bytes.toBytes(MetaSchama.CO_FILE_SIZE));
        scanFileInfo.addColumn(Bytes.toBytes(MetaSchama.CF_GENERAL),
                Bytes.toBytes(MetaSchama.CO_TOTAL_PAGES));
        scanFileInfo.addColumn(Bytes.toBytes(MetaSchama.CF_DOMAIN),
                Bytes.toBytes(MetaSchama.CO_SUB_DOMAIN));

        scanFileKeywords = new Scan();
        scanFileKeywords.addColumn(Bytes.toBytes(MetaSchama.CF_KEYWORDS),
                Bytes.toBytes(MetaSchama.CO_KEYWORD));
        scanFileKeywords.addColumn(Bytes.toBytes(MetaSchama.CF_KEYWORDS),
                Bytes.toBytes(MetaSchama.CO_FREQUENCY));

        try {

            scannerFileInfo = fileInfoTable.getScanner(scanFileInfo);

        } catch (IOException e) {

            e.printStackTrace();
        }

        for (Result result = scannerFileInfo.next(); result != null; result = scannerFileInfo
                .next()) {

            System.out.println(result.getColumn(
                    Bytes.toBytes(MetaSchama.CF_GENERAL),
                    Bytes.toBytes(MetaSchama.CO_FILE_NAME)));
        }

        scannerFileInfo.close();

        return fileMetadataList;
    }
}
