package club.mastershu.ads.consumer;

import club.mastershu.ads.dto.MySQLRowData;
import club.mastershu.ads.sender.ISender;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class BinlogConsumer {
    private final ISender sender;

    public BinlogConsumer(ISender sender) {
        this.sender = sender;
    }

    @KafkaListener(topics = {"ads-search-mysql-data"}, groupId = "ads-search")
    public void processMySQLRowData(ConsumerRecord<?, ?> record) {
        Optional<?> kafkaMessage = Optional.ofNullable(record.value());
        if (kafkaMessage.isPresent()) {
            Object message = kafkaMessage.get();
            MySQLRowData rowData = JSON.parseObject(message.toString(), MySQLRowData.class);
            log.info("Kafka processMySQLRowData: {}", JSON.toJSONString(rowData));
            sender.sender(rowData);
        }
    }

}
