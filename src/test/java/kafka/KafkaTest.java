package kafka;

import com.qjava.qsql.kafka.KafkaManager;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * @Author: Zhang.Jialei
 * @Date: 2020/8/9 21:54
 */
public class KafkaTest {
    public  String topic = "relu.user";//定义主题
    @Test
    public void put(){
        // 生产者配置
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "sql.zhangjialei.cn:9092");//kafka地址，多个地址用逗号分割
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, "com.qjava.qsql.kafka.CidPartitioner"); // 自定义主题分区规则
        // 消费者配置
        Properties properties2 = new Properties();
        properties2.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "sql.zhangjialei.cn:9092");//kafka地址，多个地址用逗号分割
        properties2.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties2.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties2.put(ConsumerConfig.GROUP_ID_CONFIG, "user.test"); // 消费者组id


        // 消费者
        KafkaManager KafkaManager_c = new KafkaManager(properties2);
        KafkaManager_c.buildConsumer();
        KafkaManager_c.subscribe(topic);

        Runnable runnable = KafkaManager_c.makeListener(new SubscribeListenerImpTest(), 1000);

        new Thread(runnable).start();


        // 生产者
        KafkaManager KafkaManager = new KafkaManager(properties);
        KafkaManager.buildProducer();

       // KafkaManager.put(topic, "测试2");
        try {
            KafkaManager.putSync(topic, "0", "hellow");
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
