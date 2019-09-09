package club.mastershu.ads.dto;

import club.mastershu.ads.constant.OpType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MySQLRowData {
    private String tableName;

    private Integer level;

    private OpType opType;
    private List<Map<String, String>> fieldValueMap = new ArrayList<>();
}
