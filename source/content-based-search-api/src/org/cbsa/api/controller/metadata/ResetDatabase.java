package org.cbsa.api.controller.metadata;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.cbsa.api.model.MetaSchema;

public class ResetDatabase {

    private static final Logger logger = Logger.getLogger(ResetDatabase.class
            .getName());

    static {
        logger.setLevel(Level.INFO);
    }

    @SuppressWarnings("deprecation")
    public static void main(String[] args) {

        Configuration conf = HBaseConfiguration.create();
        HBaseAdmin admin = null;

        try {
            admin = new HBaseAdmin(conf);
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("hbase admin failed");
        }

        try {

            admin.disableTable(MetaSchema.TB_FILE_INFO);
            admin.disableTable(MetaSchema.TB_FILE_KEYWORDS);

            admin.deleteTable(MetaSchema.TB_FILE_INFO);
            admin.deleteTable(MetaSchema.TB_FILE_KEYWORDS);

            logger.info("database reset successful");

        } catch (IOException e) {

            e.printStackTrace();
            logger.info("database reset failed");
        }

    }

}
