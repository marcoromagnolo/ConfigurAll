package configurall.internal;

import java.security.SecureClassLoader;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author Marco Romagnolo
 */
public class ConfigClassLoader extends SecureClassLoader {

    private ConcurrentMap<String, String> mirrors = new ConcurrentHashMap<>();

    public ConfigClassLoader() {
        super(ConfigClassLoader.class.getClassLoader());
    }

    public Class<?> getClass(String className) throws ClassNotFoundException {
        if (mirrors.containsKey(className)) {
            className = mirrors.get(className);
        }
        return super.findLoadedClass(className);
    }

    public Class<?> defineClass(String className, byte[] bytes) {
        String mirrorClass = className + "Super";
        mirrors.put(className, mirrorClass);
        return super.defineClass(mirrorClass, bytes, 0, bytes.length);
    }

}
