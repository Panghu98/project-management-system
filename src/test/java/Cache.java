import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Cache {
    static Map<String, Object> map = new HashMap<>();
    static ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    static Lock r = rwl.readLock();
    static Lock w = rwl.writeLock();// 获取一个key对应的value

    public static Object get(String key) {
        r.lock();
        try {
            Object value = map.get(key);
            System.out.println("线程"+Thread.currentThread().getName()+value);
            return value;
        } finally {
            r.unlock();
        }
    }// 设置key对应的value，并返回旧的value

    public static Object put(String key, Object value) {
        w.lock();
        try {
            System.err.println("线程"+Thread.currentThread().getName()+value);
            return map.put(key, value);
        } finally {
            w.unlock();
        }
    }// 清空所有的内容

    public static void clear() {
        w.lock();
        try {
            map.clear();
        } finally {
            w.unlock();
        }
    }
}