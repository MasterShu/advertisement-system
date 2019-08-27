package club.mastershu.ads.index.unit;

import club.mastershu.ads.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class UnitIndex implements IndexAware<Long, UnitObject> {
    private static Map<Long, UnitObject> objectMap;
    static {
        objectMap = new ConcurrentHashMap<>();
    }

    public Set<Long> match(Integer positionType) {
        Set<Long> unitIds = new HashSet<>();
        objectMap.forEach((k, v) -> {
            if (UnitObject.isAdSlotTypeOk(positionType, v.getPositionType())) {
                unitIds.add(k);
            }
        });
        return unitIds;
    }

    public List<UnitObject> fetch(Collection<Long> unitIds) {
        if (CollectionUtils.isEmpty(unitIds)) {
            return Collections.emptyList();
        }
        List<UnitObject> unitObjects = new ArrayList<>();
        unitIds.forEach(u -> {
            UnitObject object = get(u);
            if (null == object) {
                log.error("UnitObject not found {}", u);
                return;
            }
            unitObjects.add(object);
        });
        return unitObjects;
    }

    @Override
    public UnitObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, UnitObject value) {
        log.info("before add: {}", objectMap);
        objectMap.put(key, value);
        log.info("after add: {}", objectMap);
    }

    @Override
    public void update(Long key, UnitObject value) {
        log.info("before update: {}", objectMap);
        UnitObject oldObject = objectMap.get(key);
        if (null == oldObject) {
            objectMap.put(key, value);
        } else {
            oldObject.update(value);
        }
        log.info("after update: {}", objectMap);
    }

    @Override
    public void delete(Long key, UnitObject value) {
        log.info("before delete: {}", objectMap);
        objectMap.remove(key);
        log.info("after delete: {}", objectMap);
    }
}
