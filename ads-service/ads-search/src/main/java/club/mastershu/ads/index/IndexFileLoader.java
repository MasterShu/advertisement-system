package club.mastershu.ads.index;

import club.mastershu.ads.dump.Constant;
import club.mastershu.ads.dump.table.*;
import club.mastershu.ads.handler.LevelDataHandler;
import club.mastershu.ads.constant.OpType;
import com.alibaba.fastjson.JSON;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Component
@DependsOn("dataTable")
public class IndexFileLoader {

    public void init() {
        List<String> planStrings = loadDumpData(String.format("%s%s", Constant.DATA_ROOT_DIR, Constant.PLAN));
        planStrings.forEach(p -> LevelDataHandler.handleLevel2(JSON.parseObject(p, PlanTable.class), OpType.ADD));

        List<String> creativeStrings = loadDumpData(String.format("%s%s", Constant.DATA_ROOT_DIR, Constant.CREATIVE));
        creativeStrings.forEach(c -> LevelDataHandler.handleLevel2(JSON.parseObject(c, CreativeTable.class), OpType.ADD));

        List<String> unitStrings = loadDumpData(String.format("%s%s", Constant.DATA_ROOT_DIR, Constant.UNIT));
        unitStrings.forEach(u -> LevelDataHandler.handleLevel3(JSON.parseObject(u, UnitTable.class), OpType.ADD));

        List<String> creativeUnitStrings = loadDumpData(String.format("%s%s", Constant.DATA_ROOT_DIR, Constant.CREATIVE_UNIT));
        creativeUnitStrings.forEach(cu -> LevelDataHandler.handleLevel3(JSON.parseObject(cu, CreativeUnitTable.class), OpType.ADD));

        List<String> unitDistrictStrings = loadDumpData(String.format("%s%s", Constant.DATA_ROOT_DIR, Constant.UNIT_DISTRICT));
        unitDistrictStrings.forEach(d -> LevelDataHandler.handleLevel4(JSON.parseObject(d, UnitDistrictTable.class), OpType.ADD));

        List<String> unitItStrings = loadDumpData(String.format("%s%s", Constant.DATA_ROOT_DIR, Constant.UNIT_IT));
        unitItStrings.forEach(i -> LevelDataHandler.handleLevel4(JSON.parseObject(i, UnitItTable.class), OpType.ADD));

        List<String> unitKeywordStrings = loadDumpData(String.format("%s%s", Constant.DATA_ROOT_DIR, Constant.UNIT_KEYWORD));
        unitKeywordStrings.forEach(k -> LevelDataHandler.handleLevel4(JSON.parseObject(k, UnitKeywordTable.class), OpType.ADD));

    }

    private List<String> loadDumpData(String filename) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(filename))) {
            return bufferedReader.lines().collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
