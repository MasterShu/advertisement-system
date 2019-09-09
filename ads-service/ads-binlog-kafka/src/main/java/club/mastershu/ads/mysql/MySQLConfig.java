package club.mastershu.ads.mysql;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "system.mysql")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySQLConfig {
    private String host;
    private Integer port;
    private String username;
    private String password;
    private String binlogName;
    private Long position;
}
