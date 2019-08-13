package club.mastershu.ads.mysql;

import club.mastershu.ads.mysql.constant.OpType;
import club.mastershu.ads.mysql.dto.ParseTemplate;
import club.mastershu.ads.mysql.dto.TableTemplate;
import club.mastershu.ads.mysql.dto.Template;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class TemplateHolder {
    private ParseTemplate template;
    private String SQL_SCHEMA =  "select table_schema, table_name, " +
            "column_name, ordinal_position from information_schema.columns " +
            "where table_schema = ? and table_name = ?";
    private final JdbcTemplate jdbcTemplate;

    public TemplateHolder(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostConstruct
    private void init() {
        loadJson("template.json");
    }

    public TableTemplate getTable(String tableName) {
        return template.getTableTemplateMap().get(tableName);
    }

    private void loadJson(String path) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = loader.getResourceAsStream(path);
        try {
            Template template = JSON.parseObject(inputStream, Charset.defaultCharset(), Template.class);
            this.template = ParseTemplate.parse(template);
            loadMeta();
        } catch (IOException e) {
            log.error("Loading json template error: {}", e.getMessage());
            throw new RuntimeException("Failed to parse json file");
        }
    }

    private void loadMeta() {
        for (Map.Entry<String, TableTemplate> entry : template.getTableTemplateMap().entrySet()) {
            TableTemplate table = entry.getValue();

            List<String> insertFields = table.getOpTypeListMap().get(OpType.ADD);
            List<String> updateFields = table.getOpTypeListMap().get(OpType.UPDATE);
            List<String> deleteFields = table.getOpTypeListMap().get(OpType.DELETE);
            jdbcTemplate.query(SQL_SCHEMA, new Object[] {
                    template.getDatabase(), table.getTableName()
            }, (rs, i) -> {
                int pos = rs.getInt("ORDINAL_POSITION");
                String columnName = rs.getString("COLUMN_NAME");
                if ((null != insertFields && insertFields.contains(columnName))
                        || (null != updateFields && updateFields.contains(columnName))
                        || (null != deleteFields && deleteFields.contains(columnName))) {
                    table.getPosMap().put(pos - 1, columnName);
                }
                return null;
            });
        }
    }
}
