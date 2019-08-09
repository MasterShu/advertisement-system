package club.mastershu.ads.utils;

import java.util.Map;
import java.util.function.Supplier;

public class CommonUtils {
    public static <K, V> V gotCreate(K key, Map<K, V> map, Supplier<V> factory) {
        return map.computeIfAbsent(key, k -> factory.get());
    }
}
