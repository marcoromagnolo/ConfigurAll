package configurall;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Properties;

/**
 * @author Marco Romagnolo
 */
public class ConfigProperties {

    private Properties properties;
    private String fileName;

    public ConfigProperties(Properties properties) {
        if (properties != null) {
            this.properties = properties;
        }
    }

    public ConfigProperties(String fileName, InputStream inputStream) throws IOException {
        this.fileName = fileName;
        properties = new Properties();
        properties.load(inputStream);
    }

    public Properties getProperties() {
        return properties;
    }

    public String getPropertiesAsString() {
        StringBuilder sb = new StringBuilder("-- ").append(fileName).append(" --\n");
        for (Map.Entry property : properties.entrySet()) {
            sb.append(property.getKey()).append(": ").append(property.getValue()).append("\n");
        }
        sb.append("\n");
        return sb.toString();
    }

    public boolean isEmpty() {
        return properties.isEmpty();
    }

    public String get(String key) {
        return properties.getProperty(key);
    }

    public String getAsString(String key) {
        return get(key);
    }

    public Integer getAsInteger(String key) {
        return Integer.valueOf(get(key));
    }

    public Long getAsLong(String key) {
        return Long.valueOf(get(key));
    }

    public Float getAsFloat(String key) {
        return Float.valueOf(get(key));
    }

    public Double getAsDouble(String key) {
        return Double.valueOf(get(key));
    }

    public char getAsChar(String key) {
        String value = get(key);
        if (value == null || value.isEmpty()) {
            return 0;
        } else {
            return value.charAt(0);
        }
    }

    public Boolean getAsBoolean(String key) {
        return Boolean.valueOf(get(key));
    }

    public Byte getAsByte(String key) {
        return Byte.valueOf(get(key));
    }
}
