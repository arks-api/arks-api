package com.modules.metadata;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.client.HBaseAdmin;

@SuppressWarnings("deprecation")
public class MetadataTables {

    public static boolean createTables() {

        Configuration config = HBaseConfiguration.create();
        HBaseAdmin admin = null;

        try {

            admin = new HBaseAdmin(config);

        } catch (IOException e) {

            e.printStackTrace();
            return false;
        }

        HTableDescriptor fileInfoTable = new HTableDescriptor(
                MetaSchama.TB_FILE_INFO);
        fileInfoTable.addFamily(new HColumnDescriptor(MetaSchama.CF_GENERAL));
        fileInfoTable.addFamily(new HColumnDescriptor(MetaSchama.CF_DOMAIN));
        fileInfoTable.addFamily(new HColumnDescriptor(MetaSchama.CF_FILE_ID));

        HTableDescriptor fileKeywords = new HTableDescriptor(
                MetaSchama.TB_FILE_KEYWORDS);
        fileKeywords.addFamily(new HColumnDescriptor(MetaSchama.CF_FILE_ID));
        fileKeywords.addFamily(new HColumnDescriptor(MetaSchama.CF_KEYWORDS));

        try {

            if (!admin.tableExists(MetaSchama.TB_FILE_INFO)) {

                admin.createTable(fileInfoTable);
            }

            if (!admin.tableExists(MetaSchama.TB_FILE_KEYWORDS)) {

                admin.createTable(fileKeywords);
            }

        } catch (IOException e) {

            e.printStackTrace();
            return false;
        }

        return true;
    }
}
