package com.haocdp.storm.wordcount_kafka;

/**
 * <br/>create at 16-1-22
 *
 * @author liuxh
 * @since 1.0.0
 */
public final class BaseConfigConstants {
    private BaseConfigConstants() {
    }

//    public static final String ZK_SERVER = "zk1:2181,zk2:2182,zk3:2183";
//    public static final String BROKER_SERVER = "broker:9092";
//    public static final String ZK_SERVER = "localhost:2181";
//    public static final String BROKER_SERVER = "localhost:9092";
    public static final String ZK_SERVER = "server2:2182,server3:2183,server4:2184";
    public static final String BROKER_SERVER = "localhost:9092";
}
