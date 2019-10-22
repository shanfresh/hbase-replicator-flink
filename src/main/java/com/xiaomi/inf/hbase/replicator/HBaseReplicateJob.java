/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.xiaomi.inf.hbase.replicator;

import com.xiaomi.inf.hbase.replicator.constants.ParameterConstant;
import com.xiaomi.inf.hbase.replicator.model.HBaseOperationWrapper;
import com.xiaomi.inf.hbase.replicator.model.HBaseOperationWrapperBuilder;
import com.xiaomi.inf.hbase.replicator.sink.HBaseClientSink;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.utils.ParameterTool;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * Skeleton for a Flink Streaming Job.
 *
 * <p>For a tutorial how to write a Flink streaming application, check the
 * tutorials and examples on the <a href="http://flink.apache.org/docs/stable/">Flink Website</a>.
 *
 * <p>To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * <p>If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
public class HBaseReplicateJob {

	public static void main(String[] args) throws Exception {
		// set up the streaming execution environment
		final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
		// 根据传入的参数  --source-hdfs-input-path --targetClusterZKAddress
		ParameterTool parameter = ParameterTool.fromArgs(args);
		env.getConfig().setGlobalJobParameters(parameter);

		DataStream<String> hbaseReplicatorHDFSSource = env
			.readTextFile(parameter.get(ParameterConstant.SOURCE_HBASE_HDFS_PATH_KEY))
			.setParallelism(4);

		MapFunction<String, HBaseOperationWrapper> trMapFunction = new MapFunction<String, HBaseOperationWrapper>() {
			@Override
			public HBaseOperationWrapper map(String value) throws Exception {
				return new HBaseOperationWrapperBuilder().setHbaseReginServerName("Region-1").setWalRow(value)
						.createHBaseOperationWrapper();
			}
		};
		hbaseReplicatorHDFSSource.map(trMapFunction).addSink(new HBaseClientSink());

		env.execute("XiaoMi HBase Replicating FLink Job");
	}
}
