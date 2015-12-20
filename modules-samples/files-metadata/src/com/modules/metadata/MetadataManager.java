package com.modules.metadata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
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

        Put putFileInfo = new Put(Bytes.toBytes("1"));

        putFileInfo.add(Bytes.toBytes(MetaSchama.CF_GENERAL),
                Bytes.toBytes(MetaSchama.CO_FILE_NAME),
                Bytes.toBytes(fileMetadata.getFileName()));
        putFileInfo.add(Bytes.toBytes(MetaSchama.CF_GENERAL),
                Bytes.toBytes(MetaSchama.CO_FILE_SIZE),
                Bytes.toBytes(fileMetadata.getFileSize()));
        putFileInfo.add(Bytes.toBytes(MetaSchama.CF_GENERAL),
                Bytes.toBytes(MetaSchama.CO_TOTAL_PAGES),
                Bytes.toBytes(fileMetadata.getTotalPages()));

        List<String> fileDomains = fileMetadata.getFileDomains();

        for (int i = 0; i < fileDomains.size(); i++) {

            putFileInfo.add(Bytes.toBytes(MetaSchama.CF_DOMAIN),
                    Bytes.toBytes(MetaSchama.CO_SUB_DOMAIN + i),
                    Bytes.toBytes(fileDomains.get(i)));
        }

        putFileInfo.add(Bytes.toBytes(MetaSchama.CF_FILE_ID),
                Bytes.toBytes(MetaSchama.CO_ID), Bytes.toBytes("1"));

        List<Put> putKeywordsList = new ArrayList<Put>();

        List<String> fileKeywords = fileMetadata.getKeywords();

        for (int i = 0; i < fileKeywords.size(); i++) {

            Put putKeywords = new Put(Bytes.toBytes(String.valueOf(i+1)));

            putKeywords.add(Bytes.toBytes(MetaSchama.CF_FILE_ID),
                    Bytes.toBytes(MetaSchama.CO_ID), Bytes.toBytes("1"));
            putKeywords.add(Bytes.toBytes(MetaSchama.CF_KEYWORDS),
                    Bytes.toBytes(MetaSchama.CO_KEYWORD),
                    Bytes.toBytes(fileKeywords.get(i)));

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
}
