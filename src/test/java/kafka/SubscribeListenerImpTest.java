package kafka;

import com.qjava.qsql.kafka.SubscribeListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

/**
 * @Author: Zhang.Jialei
 * @Date: 2020/8/15 17:02
 */
public class SubscribeListenerImpTest implements SubscribeListener {

    @Override
    public void onInbound(ConsumerRecords<String, String> records) {
        //
        for (ConsumerRecord<String, String> record : records) {
            System.out.println(String.format("topic:%s,offset:%d,消息:%s",
                    record.topic(), record.offset(),  record.value()));
        }
    }
}
