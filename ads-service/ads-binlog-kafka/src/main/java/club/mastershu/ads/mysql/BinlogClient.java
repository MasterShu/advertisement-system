package club.mastershu.ads.mysql;

import club.mastershu.ads.mysql.listener.AggregationListener;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class BinlogClient {
    private BinaryLogClient client;

    private final MySQLConfig config;

    private final AggregationListener aggregationListener;

    public BinlogClient(MySQLConfig config, AggregationListener aggregationListener) {
        this.config = config;
        this.aggregationListener = aggregationListener;
    }

    public void connect() {
        new Thread(() -> {
            client = new BinaryLogClient(
                    config.getHost(),
                    config.getPort(),
                    config.getUsername(),
                    config.getPassword()
            );
            if (!StringUtils.isEmpty(config.getBinlogName()) && !config.getPosition().equals(-1L)) {
                client.setBinlogFilename(config.getBinlogName());
                client.setBinlogPosition(config.getPosition());
            }
            client.registerEventListener(aggregationListener);
            try {
                log.info("Start connecting MySQL ...");
                client.connect();
                log.info("Done connecting MySQL.");
            } catch (IOException ex) {
                log.error("Connecting MySQL error: {}", ex.getMessage());
                ex.printStackTrace();
            }
        }).start();
    }

    public void close() {
        try {
            client.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
