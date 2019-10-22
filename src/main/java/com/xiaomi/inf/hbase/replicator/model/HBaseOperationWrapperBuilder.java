package com.xiaomi.inf.hbase.replicator.model;

public class HBaseOperationWrapperBuilder {
    private String hbaseReginServerName;
    private String walRow;

    public HBaseOperationWrapperBuilder setHbaseReginServerName(String hbaseReginServerName) {
        this.hbaseReginServerName = hbaseReginServerName;
        return this;
    }

    public HBaseOperationWrapperBuilder setWalRow(String walRow) {
        this.walRow = walRow;
        return this;
    }

    public HBaseOperationWrapper createHBaseOperationWrapper() {
        return new HBaseOperationWrapper(hbaseReginServerName, walRow);
    }
}