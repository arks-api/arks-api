package org.cbsa.api.controller.metadata;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.cbsa.api.model.MetaSchama;

public class ResetDatabase {

    public static void main(String[] args) {

        Log log = LogFactory.getLog(ResetDatabase.class);

        Configuration conf = HBaseConfiguration.create();
        HBaseAdmin admin = null;

        try {
            admin = new HBaseAdmin(conf);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("HBase Admin failed");
        }

        try {

            admin.disableTable(MetaSchama.TB_FILE_INFO);
            admin.disableTable(MetaSchama.TB_FILE_KEYWORDS);

            admin.deleteTable(MetaSchama.TB_FILE_INFO);
            admin.deleteTable(MetaSchama.TB_FILE_KEYWORDS);

            log.info("Database Reset Successful");

        } catch (IOException e) {

            e.printStackTrace();
            log.error("Database Reset Failed");
        }

    }

}
