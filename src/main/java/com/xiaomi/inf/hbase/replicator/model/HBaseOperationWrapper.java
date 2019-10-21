package com.xiaomi.inf.hbase.replicator;

import java.io.Serializable;

/**
 * @author shanjixi on 19/10/21.
 */
public class HBaseOperationWrapper implements Serializable {
    private String hbaseReginServerName;
    private String walRow;
    public String getHbaseReginServerName() {
        return hbaseReginServerName;
    }

    public void setHbaseReginServerName(String hbaseReginServerName) {
        this.hbaseReginServerName = hbaseReginServerName;
    }

    public String getWalRow() {
        return walRow;
    }

    public void setWalRow(String walRow) {
        this.walRow = walRow;
    }
}
