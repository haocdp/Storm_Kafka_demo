package com.haocdp.storm.gps.topology;

import backtype.storm.Config;
import backtype.storm.LocalCluster;
import backtype.storm.StormSubmitter;
import backtype.storm.spout.SchemeAsMultiScheme;
import backtype.storm.topology.TopologyBuilder;
import backtype.storm.tuple.Fields;
import com.haocdp.storm.gps.bolt.GPSHandlerBolt1;
import com.haocdp.storm.gps.bolt.GPSHandlerBolt2;
import com.haocdp.storm.gps.bolt.GPSHandlerBolt3;
import com.haocdp.storm.wordcount_kafka.BaseConfigConstants;
import com.haocdp.storm.wordcount_kafka.MessageScheme;
import storm.kafka.BrokerHosts;
import storm.kafka.KafkaSpout;
import storm.kafka.SpoutConfig;
import storm.kafka.ZkHosts;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by haoc_dp on 2016/12/20.
 */
public class GPSTopology {
    private static final String KAFKA_SPOUT = "GPSReadSpout";
    private static final String HANDLER_BOLT_1 = "GPSHandlerBolt1";
    private static final String HANDLER_BOLT_2 = "GPSHandlerBolt2";
    private static final String HANDLER_BOLT_3 = "GPSHandlerBolt3";
    private static final String PRODUCER_TOPIC = "gps";
    private static final String ZK_ROOT = "/storm/topology/gps";
    private static final String ZK_ID = "write";

    public static void main(String[] args)throws Exception {
        // 配置Zookeeper地址
        BrokerHosts brokerHosts = new ZkHosts("server2:2182,server3:2183,server4:2184");
        // 配置Kafka订阅的Topic，以及zookeeper中数据节点目录和名字
        SpoutConfig spoutConfig = new SpoutConfig(brokerHosts, PRODUCER_TOPIC, ZK_ROOT, ZK_ID);
        spoutConfig.scheme = new SchemeAsMultiScheme(new MessageScheme());

        TopologyBuilder builder = new TopologyBuilder();
        builder.setSpout(KAFKA_SPOUT, new KafkaSpout(spoutConfig));
        builder.setBolt(HANDLER_BOLT_1, new GPSHandlerBolt1(),3).shuffleGrouping(KAFKA_SPOUT);
        /*
        builder.setBolt(HANDLER_BOLT_2, new GPSHandlerBolt2()).shuffleGrouping(KAFKA_SPOUT);
        builder.setBolt(HANDLER_BOLT_3, new GPSHandlerBolt3()).shuffleGrouping(KAFKA_SPOUT);
        */
        Config config = new Config();
        Map<String, String> map = new HashMap<>();
        map.put("metadata.broker.list", BaseConfigConstants.BROKER_SERVER);// 配置Kafka broker地址
        map.put("serializer.class", "kafka.serializer.StringEncoder");// serializer.class为消息的序列化类
        config.put("kafka.broker.properties", map);// 配置KafkaBolt中的kafka.broker.properties
        config.put("topic", PRODUCER_TOPIC);// 配置KafkaBolt生成的topic

        if (args.length == 0) {
            LocalCluster cluster = new LocalCluster();
            cluster.submitTopology("GPSTopology", config, builder.createTopology());
//            Utils.sleep(100000);
//            cluster.killTopology(DEFAULT_TOPOLOGY_NAME);
//            cluster.shutdown();
        } else {
            config.setNumWorkers(1);
            StormSubmitter.submitTopology(args[0], config, builder.createTopology());
        }
    }
}
