package club.mastershu.ads.sender.index;

import club.mastershu.ads.dump.table.*;
import club.mastershu.ads.handler.LevelDataHandler;
import club.mastershu.ads.index.DataLevel;
import club.mastershu.ads.mysql.constant.Constant;
import club.mastershu.ads.mysql.dto.MySQLRowData;
import club.mastershu.ads.sender.ISender;
import club.mastershu.ads.utils.CommonUtils;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@Component("indexSender")
public class IndexSender implements ISender {

    @Override
    public void sender(MySQLRowData rowData) {
        Integer level = rowData.getLevel();
        if (DataLevel.LEVEL2.getLevel().equals(level)) {
            Level2RowData(rowData);
        } else if (DataLevel.LEVEL3.getLevel().equals(level)) {
            Level3RowData(rowData);
        } else if (DataLevel.LEVEL4.getLevel().equals(level)) {
            Level4RowData(rowData);
        } else {
            log.error("MySQLRowData error: {}", JSON.toJSONString(rowData));
        }
    }

    private void Level2RowData(MySQLRowData rowData) {
        if (rowData.getTableName().equals(Constant.PLAN_TABLE_INFO.TABLE_NAME)) {
            List<PlanTable> planTables = new ArrayList<>();
            for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                PlanTable planTable = new PlanTable();
                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.PLAN_TABLE_INFO.COLUMN_ID:
                            planTable.setId(Long.valueOf(v));
                            break;
                        case Constant.PLAN_TABLE_INFO.COLUMN_USER_ID:
                            planTable.setUserId(Long.valueOf(v));
                            break;
                        case Constant.PLAN_TABLE_INFO.COLUMN_STATUS:
                            planTable.setStatus(Integer.valueOf(v));
                            break;
                        case Constant.PLAN_TABLE_INFO.COLUMN_START_TIME:
                            planTable.setStartTime(CommonUtils.parseStringDate(v));
                            break;
                        case Constant.PLAN_TABLE_INFO.COLUMN_END_TIME:
                            planTable.setEndTime(CommonUtils.parseStringDate(v));
                            break;
                    }
                });
                planTables.add(planTable);
            }
            planTables.forEach(p -> LevelDataHandler.handleLevel2(p, rowData.getOpType()));
        } else if (rowData.getTableName().equals(Constant.CREATIVE_TABLE_INFO.TABLE_NAME)) {
            List<CreativeTable> creativeTables = new ArrayList<>();
            for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                CreativeTable creativeTable = new CreativeTable();
                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.CREATIVE_TABLE_INFO.COLUMN_ID:
                            creativeTable.setId(Long.valueOf(v));
                            break;
                        case Constant.CREATIVE_TABLE_INFO.COLUMN_TYPE:
                            creativeTable.setType(Integer.valueOf(v));
                            break;
                        case Constant.CREATIVE_TABLE_INFO.COLUMN_MATERIAL_TYPE:
                            creativeTable.setMaterialType(Integer.valueOf(v));
                            break;
                        case Constant.CREATIVE_TABLE_INFO.COLUMN_HEIGHT:
                            creativeTable.setHeight(Integer.valueOf(v));
                            break;
                        case Constant.CREATIVE_TABLE_INFO.COLUMN_WIDTH:
                            creativeTable.setWidth(Integer.valueOf(v));
                            break;
                        case Constant.CREATIVE_TABLE_INFO.COLUMN_AUDIT_STATUS:
                            creativeTable.setAuditStatus(Integer.valueOf(v));
                            break;
                        case Constant.CREATIVE_TABLE_INFO.COLUMN_URL:
                            creativeTable.setUrl(String.valueOf(v));
                            break;
                    }
                });
                creativeTables.add(creativeTable);
            }
            creativeTables.forEach(c -> LevelDataHandler.handleLevel2(c, rowData.getOpType()));
        }
    }

    private void Level3RowData(MySQLRowData rowData) {
        if (rowData.getTableName().equals(Constant.UNIT_TABLE_INFO.TABLE_NAME)) {
            List<UnitTable> unitTables = new ArrayList<>();
            for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                UnitTable unitTable = new UnitTable();
                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.UNIT_TABLE_INFO.COLUMN_ID:
                            unitTable.setId(Long.valueOf(v));
                            break;
                        case Constant.UNIT_TABLE_INFO.COLUMN_STATUS:
                            unitTable.setStatus(Integer.valueOf(v));
                            break;
                        case Constant.UNIT_TABLE_INFO.COLUMN_POSITION_TYPE:
                            unitTable.setPositionType(Integer.valueOf(v));
                            break;
                        case Constant.UNIT_TABLE_INFO.COLUMN_PLAN_ID:
                            unitTable.setPlanId(Long.valueOf(v));
                            break;
                    }
                });
                unitTables.add(unitTable);
            }
            unitTables.forEach(u -> LevelDataHandler.handleLevel3(u, rowData.getOpType()));
        } else if (rowData.getTableName().equals(Constant.CREATIVE_UNIT_TABLE_INFO.TABLE_NAME)) {
            List<CreativeUnitTable> creativeUnitTables = new ArrayList<>();
            for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                CreativeUnitTable creativeUnitTable = new CreativeUnitTable();
                fieldValueMap.forEach((k, v) -> {
                    switch (k) {
                        case Constant.CREATIVE_UNIT_TABLE_INFO.COLUMN_CREATIVE_ID:
                            creativeUnitTable.setCreativeId(Long.valueOf(v));
                            break;
                        case Constant.CREATIVE_UNIT_TABLE_INFO.COLUMN_UNIT_ID:
                            creativeUnitTable.setUnitId(Long.valueOf(v));
                            break;
                    }
                });
                creativeUnitTables.add(creativeUnitTable);
            }
            creativeUnitTables.forEach(creativeUnitTable -> LevelDataHandler.handleLevel3(creativeUnitTable, rowData.getOpType()));
        }
    }

    private void Level4RowData(MySQLRowData rowData) {
        switch (rowData.getTableName()) {
            case Constant.UNIT_DISTRICT_TABLE_INFO.TABLE_NAME:
                List<UnitDistrictTable> unitDistrictTables = new ArrayList<>();
                for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                    UnitDistrictTable unitDistrictTable = new UnitDistrictTable();
                    fieldValueMap.forEach((k, v) -> {
                        switch (k) {
                            case Constant.UNIT_DISTRICT_TABLE_INFO.COLUMN_UNIT_ID:
                                unitDistrictTable.setUnitId(Long.valueOf(v));
                                break;
                            case Constant.UNIT_DISTRICT_TABLE_INFO.COLUMN_PROVINCE:
                                unitDistrictTable.setProvince(String.valueOf(v));
                                break;
                            case Constant.UNIT_DISTRICT_TABLE_INFO.COLUMN_CITY:
                                unitDistrictTable.setCity(String.valueOf(v));
                                break;
                        }
                    });
                    unitDistrictTables.add(unitDistrictTable);
                }
                unitDistrictTables.forEach(unitDistrictTable -> LevelDataHandler.handleLevel4(unitDistrictTable, rowData.getOpType()));
                break;
            case Constant.UNIT_IT_TABLE_INFO.TABLE_NAME:
                List<UnitItTable> unitItTables = new ArrayList<>();
                for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                    UnitItTable unitItTable = new UnitItTable();
                    fieldValueMap.forEach((k, v) -> {
                        switch (k) {
                            case Constant.UNIT_IT_TABLE_INFO.COLUMN_UNIT_ID:
                                unitItTable.setUnitId(Long.valueOf(v));
                                break;
                            case Constant.UNIT_IT_TABLE_INFO.COLUMN_TAG:
                                unitItTable.setTag(String.valueOf(v));
                                break;
                        }
                    });
                    unitItTables.add(unitItTable);
                }
                unitItTables.forEach(unitItTable -> LevelDataHandler.handleLevel4(unitItTable, rowData.getOpType()));
                break;
            case Constant.UNIT_KEYWORD_TABLE_INFO.TABLE_NAME:
                List<UnitKeywordTable> unitKeywordTables = new ArrayList<>();
                for (Map<String, String> fieldValueMap : rowData.getFieldValueMap()) {
                    UnitKeywordTable unitKeywordTable = new UnitKeywordTable();
                    fieldValueMap.forEach((k, v) -> {
                        switch (k) {
                            case Constant.UNIT_KEYWORD_TABLE_INFO.COLUMN_UNIT_ID:
                                unitKeywordTable.setUnitId(Long.valueOf(v));
                                break;
                            case Constant.UNIT_KEYWORD_TABLE_INFO.COLUMN_KEYWORD:
                                unitKeywordTable.setKeyword(String.valueOf(v));
                                break;
                        }
                    });
                    unitKeywordTables.add(unitKeywordTable);
                }
                unitKeywordTables.forEach(unitKeywordTable -> LevelDataHandler.handleLevel4(unitKeywordTable, rowData.getOpType()));
                break;
        }
    }
}
