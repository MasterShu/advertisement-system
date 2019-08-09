package club.mastershu.ads.index.plan;

import club.mastershu.ads.index.IndexAware;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
public class PlanIndex implements IndexAware<Long, PlanObject> {

    private static Map<Long, PlanObject> objectMap;

    static {
        objectMap = new ConcurrentHashMap<>();
    }

    @Override
    public PlanObject get(Long key) {
        return objectMap.get(key);
    }

    @Override
    public void add(Long key, PlanObject value) {
        log.info("before add: {}", objectMap);
        objectMap.put(key, value);
        log.info("after add: {}", objectMap);
    }

    @Override
    public void update(Long key, PlanObject value) {
        log.info("before update: {}", objectMap);
        PlanObject oldPlanObject = objectMap.get(key);
        if (null == oldPlanObject) {
            objectMap.put(key, value);
        } else {
            oldPlanObject.update(value);
        }
        log.info("after update: {}", objectMap);
    }

    @Override
    public void delete(Long key, PlanObject value) {
        log.info("before delete: {}", objectMap);
        objectMap.remove(key);
        log.info("after delete: {}", objectMap);
    }
}
