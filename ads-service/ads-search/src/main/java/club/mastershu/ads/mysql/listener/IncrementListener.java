package club.mastershu.ads.mysql.listener;


import club.mastershu.ads.mysql.constant.Constant;
import club.mastershu.ads.mysql.constant.OpType;
import club.mastershu.ads.mysql.dto.BinlogRowData;
import club.mastershu.ads.mysql.dto.MySQLRowData;
import club.mastershu.ads.mysql.dto.TableTemplate;
import club.mastershu.ads.sender.ISender;
import com.github.shyiko.mysql.binlog.event.EventType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class IncrementListener implements Ilistener {

    @Resource(name = "")
    private ISender sender;

    private final AggregationListener aggregationListener;

    public IncrementListener(AggregationListener aggregationListener) {
        this.aggregationListener = aggregationListener;
    }

    @Override
    @PostConstruct
    public void register() {
        log.info("IncrementListener register database and table info");
        Constant.tableToDb.forEach((k, v) -> aggregationListener.register(v, k, this));
    }

    @Override
    public void onEvent(BinlogRowData eventData) {
        TableTemplate table = eventData.getTable();
        EventType eventType = eventData.getEventType();

        MySQLRowData rowData = new MySQLRowData();
        rowData.setTableName(table.getTableName());
        rowData.setLevel(eventData.getTable().getLevel());
        OpType opType = OpType.to(eventType);
        rowData.setOpType(opType);

        List<String> fieldList = table.getOpTypeListMap().get(opType);
        if (null == fieldList) {
            log.warn("{} is not supported for {}", opType, table.getTableName());
            return;
        }
        for (Map<String, String> afterMap : eventData.getAfter()) {
            Map<String, String> _afterMap = new HashMap<>();
            for (Map.Entry<String, String> entry : afterMap.entrySet()) {
                String columnName = entry.getKey();
                String columnValue = entry.getValue();
                _afterMap.put(columnName, columnValue);
            }
            rowData.getFieldValueMap().add(_afterMap);
        }
        sender.sender(rowData);
    }
}
