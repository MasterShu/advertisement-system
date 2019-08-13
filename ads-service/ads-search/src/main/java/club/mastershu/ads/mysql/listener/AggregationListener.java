package club.mastershu.ads.mysql.listener;

import club.mastershu.ads.mysql.TemplateHolder;
import club.mastershu.ads.mysql.dto.BinlogRowData;
import club.mastershu.ads.mysql.dto.TableTemplate;
import com.github.shyiko.mysql.binlog.BinaryLogClient;
import com.github.shyiko.mysql.binlog.event.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.hibernate.engine.spi.IdentifierValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.*;
import java.util.function.ObjIntConsumer;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AggregationListener implements BinaryLogClient.EventListener {

    private String dbname;
    private String tableName;
    private Map<String, Ilistener> listenerMap = new HashMap<>();

    private final TemplateHolder templateHolder;

    public AggregationListener(TemplateHolder templateHolder) {
        this.templateHolder = templateHolder;
    }

    private String genKey(String dbname, String tableName) {
        return dbname + ":" + tableName;
    }

    public void register(String _dbname, String _tableName, Ilistener ilistener) {
        log.info("register: {}-{}", _dbname, _tableName);
        this.listenerMap.put(genKey(_dbname, _tableName), ilistener);
    }

    @Override
    public void onEvent(Event event) {
        EventType type = event.getHeader().getEventType();
        log.debug("event type: {}", type);

        if (type == EventType.TABLE_MAP) {
            TableMapEventData data = event.getData();
            this.tableName = data.getTable();
            this.dbname = data.getDatabase();
            return;
        }
        if (type != EventType.EXT_WRITE_ROWS && type != EventType.EXT_UPDATE_ROWS && type != EventType.EXT_DELETE_ROWS) {
            return;
        }

        // is database and table loaded
        if (StringUtils.isEmpty(dbname) || StringUtils.isEmpty(tableName)) {
            log.error("no meta data event");
            return;
        }

        // find registered listener
        String key = genKey(this.dbname, this.tableName);
        Ilistener listener = this.listenerMap.get(key);
        if (null == listener) {
            log.debug("skip {}", key);
            return;
        }
        log.info("trigger event: {}", type.name());

        try {
            BinlogRowData rowData = buildRowData(event.getData());
            if (null == rowData) {
                return;
            }
            rowData.setEventType(type);
            listener.onEvent(rowData);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("On event error: {}", ex.getMessage());
        } finally {
            this.tableName = "";
            this.dbname = "";
        }
    }

    private List<Serializable[]> getAfterValues(EventData eventData) {
        if (eventData instanceof WriteRowsEventData) {
            return ((WriteRowsEventData) eventData).getRows();
        }
        if (eventData instanceof UpdateRowsEventData) {
            return ((UpdateRowsEventData) eventData).getRows().stream().map(Map.Entry::getValue).collect(Collectors.toList());
        }
        if (eventData instanceof DeleteRowsEventData) {
            return ((DeleteRowsEventData) eventData).getRows();
        }
        return Collections.emptyList();
    }

    private BinlogRowData buildRowData(EventData eventData) {
        TableTemplate table = templateHolder.getTable(tableName);
        if (null == table) {
            log.warn("Table {} not found", tableName);
            return null;
        }
        List<Map<String, String>> afterMapList = new ArrayList<>();
        for (Serializable[] after : getAfterValues(eventData)) {
            Map<String, String> afterMap = new HashMap<>();
            int columnLength = after.length;
            for (int i = 0; i < columnLength; ++i) {
                // pull current column name
                String columnName = table.getPosMap().get(i);
                if (null == columnName) {
                    log.debug("ignore position: {}", i);
                    continue;
                }
                String columnValue = after[i].toString();
                afterMap.put(columnName, columnValue);
            }
            afterMapList.add(afterMap);
        }
        BinlogRowData rowData = new BinlogRowData();
        rowData.setAfter(afterMapList);
        rowData.setTable(table);
        return rowData;
    }
}
