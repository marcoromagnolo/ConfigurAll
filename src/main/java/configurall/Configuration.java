package configurall;

import configurall.internal.ConfigClassLoader;

import java.util.Map;

/**
 * @author Marco Romagnolo
 */
public interface Configuration {

    ConfigProperties getProperties();

    ConfigProperties getProperties(String fileName);

    Map<String, ConfigProperties> getAllProperties();

    ConfigClassLoader getClassLoader();
}
