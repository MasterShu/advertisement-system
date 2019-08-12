package club.mastershu.ads.index.creativeUnit;

import club.mastershu.ads.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;

@Slf4j
@Component
public class CreativeUnitIndex implements IndexAware<String, CreativeUnitObject> {

    private static Map<String, CreativeUnitObject> objectMap;
    private static Map<Long, Set<Long>> creativeUnitMap;
    private static Map<Long, Set<Long>> unitCreativeMap;
    static{
        objectMap = new ConcurrentHashMap<>();
        creativeUnitMap = new ConcurrentHashMap<>();
        unitCreativeMap = new ConcurrentHashMap<>();
    }
    @Override
    public CreativeUnitObject get(String key) {
        return objectMap.get(key);
    }

    @Override
    public void add(String key, CreativeUnitObject value) {
        log.info("CreativeUnitIndex, before add: {}", objectMap);
        objectMap.put(key, value);
        Set<Long> unitSet = creativeUnitMap.get(value.getCreativeId());
        if (CollectionUtils.isEmpty(unitSet)) {
            unitSet = new ConcurrentSkipListSet<>();
            creativeUnitMap.put(value.getCreativeId(), unitSet);
        }
        unitSet.add(value.getUnitId());

        Set<Long> creativeSet = unitCreativeMap.get(value.getUnitId());
        if (CollectionUtils.isEmpty(creativeSet)) {
            creativeSet = new ConcurrentSkipListSet<>();
            unitCreativeMap.put(value.getUnitId(), creativeSet);
        }
        creativeSet.add(value.getCreativeId());
        log.info("CreativeUnitIndex, after add: {}", objectMap);
    }

    @Override
    public void update(String key, CreativeUnitObject value) {
        log.error("CreativeUnitIndex, It index can not support update");
    }

    @Override
    public void delete(String key, CreativeUnitObject value) {
        log.info("CreativeUnitIndex, before delete: {}", objectMap);
        objectMap.remove(key);
        Set<Long> unitSet = creativeUnitMap.get(value.getCreativeId());
        if (CollectionUtils.isNotEmpty(unitSet)) {
            unitSet.remove(value.getUnitId());
        }

        Set<Long> creativeSet = unitCreativeMap.get(value.getUnitId());
        if (CollectionUtils.isNotEmpty(creativeSet)) {
            creativeSet.remove(value.getCreativeId());
        }
        log.info("CreativeUnitIndex, after delete: {}", objectMap);
    }
}