package com.xiaomi.inf.hbase.replicator.sink;

import com.xiaomi.inf.hbase.replicator.constants.ParameterConstant;
import com.xiaomi.inf.hbase.replicator.model.HBaseOperationWrapper;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.configuration.Configuration;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;

/**
 * @author shanjixi on 19/10/21.
 */
public class HBaseClientSink extends RichSinkFunction<HBaseOperationWrapper> {
    String target = "";

    @Override
    public void open(Configuration parameters) throws Exception {
        super.open(parameters);
        ParameterTool parameterTool = (ParameterTool) getRuntimeContext().getExecutionConfig().getGlobalJobParameters();
        target = parameterTool.get(ParameterConstant.TARGET_HBASE_ZK_PATH, "N/A");
        System.out.println("FLink-Sink-Opened");
    }

    @Override
    public void invoke(HBaseOperationWrapper value, Context context) throws Exception {
        System.out.println("Receive One Operation" + value + "Try to send to " + target);
    }

}
