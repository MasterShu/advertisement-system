package club.mastershu.ads.sender.kafka;

import club.mastershu.ads.dto.MySQLRowData;
import club.mastershu.ads.sender.ISender;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class KafkaSender implements ISender {
    @Value("${system.kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaSender(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sender(MySQLRowData rowData) {
        log.info("binlog kafka service send MySQLRowData...");
        kafkaTemplate.send(topic, JSON.toJSONString(rowData));
    }

}
