package com.qjava.qsql.kafka;

/**
 * @Author: Zhang.Jialei
 * @Date: 2020/8/9 23:59
 */
public interface KafkaProperties {
    String zkConnect = "10.22.10.139:2181";
    String groupId = "group1";
    String topic = "topic1";
    String kafkaServerURL = "10.22.10.139";
    int kafkaServerPort = 9092;
    int kafkaProducerBufferSize = 64 * 1024;
    int connectionTimeOut = 20000;
    int reconnectInterval = 10000;
    String topic2 = "topic2";
    String clientId = "SimpleConsumerDemoClient";
}
