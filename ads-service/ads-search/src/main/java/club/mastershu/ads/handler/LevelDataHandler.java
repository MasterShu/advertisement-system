package club.mastershu.ads.handler;

import club.mastershu.ads.dump.table.*;
import club.mastershu.ads.index.DataTable;
import club.mastershu.ads.index.IndexAware;
import club.mastershu.ads.index.creative.CreativeIndex;
import club.mastershu.ads.index.creative.CreativeObject;
import club.mastershu.ads.index.creativeUnit.CreativeUnitIndex;
import club.mastershu.ads.index.creativeUnit.CreativeUnitObject;
import club.mastershu.ads.index.district.UnitDistrictIndex;
import club.mastershu.ads.index.interest.UnitItIndex;
import club.mastershu.ads.index.keyword.UnitKeywordIndex;
import club.mastershu.ads.index.plan.PlanIndex;
import club.mastershu.ads.index.plan.PlanObject;
import club.mastershu.ads.index.unit.UnitIndex;
import club.mastershu.ads.index.unit.UnitObject;
import club.mastershu.ads.mysql.constant.OpType;
import club.mastershu.ads.utils.CommonUtils;
import com.alibaba.fastjson.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class LevelDataHandler {
    public static void handleLevel2(PlanTable planTable, OpType type) {
        PlanObject planObject = new PlanObject(
                planTable.getId(),
                planTable.getUserId(),
                planTable.getStatus(),
                planTable.getStartTime(),
                planTable.getEndTime()
        );
        handleBinlogEvent(DataTable.of(PlanIndex.class), planTable.getId(), planObject, type);
    }

    public static void handleLevel2(CreativeTable creativeTable, OpType type) {
        CreativeObject creativeObject = new CreativeObject(
                creativeTable.getId(),
                creativeTable.getName(),
                creativeTable.getType(),
                creativeTable.getMaterialType(),
                creativeTable.getHeight(),
                creativeTable.getWidth(),
                creativeTable.getAuditStatus(),
                creativeTable.getUrl()
        );
        handleBinlogEvent(DataTable.of(CreativeIndex.class), creativeTable.getId(), creativeObject, type);
    }

    public static void handleLevel3(UnitTable unitTable, OpType type) {
        PlanObject planObject = DataTable.of(PlanIndex.class).get(unitTable.getId());
        if (null == planObject) {
            log.error("handleLevel3 found PlanObject error: {}", unitTable.getPlanId());
            return;
        }
        UnitObject unitObject = new UnitObject(
                unitTable.getId(),
                unitTable.getStatus(),
                unitTable.getPositionType(),
                unitTable.getPlanId(),
                planObject
        );
        handleBinlogEvent(DataTable.of(UnitIndex.class), unitTable.getId(), unitObject, type);
    }

    public static void handleLevel3(CreativeUnitTable creativeUnitTable, OpType type) {
        if (type == OpType.UPDATE) {
            log.error("CreativeUnitIndex not supported update");
            return;
        }
        UnitObject unitObject = DataTable.of(UnitIndex.class).get(creativeUnitTable.getUnitId());
        CreativeObject creativeObject = DataTable.of(CreativeIndex.class).get(creativeUnitTable.getCreativeId());
        if (null == unitObject || null == creativeObject) {
            log.error("handleLevel3 found UnitObject or CreativeObject error: {}", JSON.toJSONString(creativeUnitTable));
            return;
        }
        CreativeUnitObject creativeUnitObject = new CreativeUnitObject(
                creativeUnitTable.getCreativeId(),
                creativeUnitTable.getUnitId()
        );
        handleBinlogEvent(
                DataTable.of(CreativeUnitIndex.class),
                CommonUtils.stringContact(
                        creativeUnitTable.getCreativeId().toString(),
                        creativeUnitTable.getUnitId().toString()
                ),
                creativeUnitObject, type
        );
    }

    public static void handleLevel4(UnitDistrictTable unitDistrictTable, OpType type) {
        if (type == OpType.UPDATE) {
            log.error("UnitDistrictIndex not supported update");
            return;
        }
        UnitObject unitObject = DataTable.of(UnitIndex.class).get(unitDistrictTable.getUnitId());
        if (null == unitObject) {
            log.error("UnitDistrictTable index error: {}", unitDistrictTable.getUnitId());
            return;
        }
        String key = CommonUtils.stringContact(unitDistrictTable.getProvince(), unitDistrictTable.getCity());
        Set<Long> value = new HashSet<>(Collections.singleton(unitDistrictTable.getUnitId()));
        handleBinlogEvent(DataTable.of(UnitDistrictIndex.class), key, value, type);
    }

    public static void handleLevel4(UnitItTable unitItTable, OpType type) {
        if (type == OpType.UPDATE) {
            log.error("UnitItIndex not supported update");
            return;
        }
        UnitObject unitObject = DataTable.of(UnitIndex.class).get(unitItTable.getUnitId());
        if (null == unitObject) {
            log.error("UnitItTable index error: {}", unitItTable.getUnitId());
            return;
        }
        Set<Long> value = new HashSet<>(Collections.singleton(unitItTable.getUnitId()));
        handleBinlogEvent(DataTable.of(UnitItIndex.class), unitItTable.getTag(), value, type);
    }

    public static void handleLevel4(UnitKeywordTable unitKeywordTable, OpType type) {
        if (type == OpType.UPDATE) {
            log.error("UnitKeywordIndex not supported update");
            return;
        }
        UnitObject unitObject = DataTable.of(UnitIndex.class).get(unitKeywordTable.getUnitId());
        if (null == unitObject) {
            log.error("UnitKeyword index error: {}", unitKeywordTable.getUnitId());
            return;
        }
        Set<Long> value = new HashSet<>(Collections.singleton(unitKeywordTable.getUnitId()));
        handleBinlogEvent(DataTable.of(UnitKeywordIndex.class), unitKeywordTable.getKeyword(), value, type);
    }

    private static <K, V> void handleBinlogEvent(IndexAware<K, V> index, K key, V value, OpType type) {
        switch (type) {
            case ADD:
                index.add(key, value);
                break;
            case UPDATE:
                index.update(key, value);
                break;
            case DELETE:
                index.delete(key, value);
                break;
            default:
                break;
        }
    }
}
