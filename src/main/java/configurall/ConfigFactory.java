package configurall;

import configurall.internal.AsmModifier;
import configurall.internal.ConfigClassLoader;

import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marco Romagnolo
 */
public class ConfigFactory implements Configuration {

    private final static Logger LOGGER = Logger.getLogger(ConfigFactory.class.getName());
    private static final ConfigFactory instance;
    private ConfigProperties defaultProp;
    private Map<String, ConfigProperties> propMap = new HashMap<>();
    private ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
    private ConfigClassLoader classLoader = new ConfigClassLoader();

    static {
        instance = new ConfigFactory();
    }

    private ConfigFactory() {
        try {
            Enumeration<URL> resources = contextClassLoader.getResources(".");
            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                Path path = Paths.get(resource.toURI());
                DirectoryStream<Path> paths = Files.newDirectoryStream(path, "*.properties");
                for (Path properties : paths) {
                    String fileName = properties.getFileName().toString();
                    InputStream stream = Files.newInputStream(properties);
                    propMap.put(fileName, new ConfigProperties(fileName, stream));
                }
            }
            if (!propMap.isEmpty()) {
                for (Map.Entry<String, ConfigProperties> entry : propMap.entrySet()) {
                    if (propMap.size() == 1) {
                        defaultProp = entry.getValue();
                    } else {
                        String key = entry.getKey();
                        if (key.equals("application") || key.equals("conf") || key.equals("config")
                                || key.equals("configuration")) {
                            defaultProp = entry.getValue();
                        }
                    }
                }
            }
            searchInto();
        } catch (URISyntaxException | IOException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    private void searchInto() throws IOException, URISyntaxException {
        Enumeration<URL> classes = contextClassLoader.getResources(".");
        while (classes.hasMoreElements()) {
            URL resource = classes.nextElement();
            searchInto(resource, "");
        }
    }

    private void searchInto(URL path, final String packageName) throws IOException, URISyntaxException {
        File current = new File(path.toURI());
        String[] dirs = current.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return new File(dir, name).isDirectory();
            }
        });
        current.list(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                String ext = ".class";
                if (name.endsWith(ext)) {
                    String className = (packageName + name).substring(0, (packageName + name).length()-ext.length());
                    AsmModifier asmParser = new AsmModifier(className, defaultProp);
                    byte[] bytes = asmParser.getBytes();
                    if (bytes != null) {
                        classLoader.defineClass(className, bytes);
                    }
                }
                return true;
            }
        });

        for (String dir : dirs) {
            searchInto(new URL(path, dir+File.separator), packageName + dir + ".");
        }

    }

    private void setDefault(String fileName) throws FileNotFoundException {
        InputStream inputStream = contextClassLoader.getResourceAsStream(fileName);
        try {
            instance.defaultProp = new ConfigProperties(fileName, inputStream);
        } catch (IOException e) {
            LOGGER.log(Level.WARNING, e.getMessage(), e);
        }
    }

    private void add(String fileName) throws IOException {
        InputStream stream = contextClassLoader.getResourceAsStream(fileName);
        ConfigProperties configProperties = new ConfigProperties(fileName, stream);
        instance.propMap.put(fileName, configProperties);
    }

    private void addAll(String[] fileNames) throws IOException {
        for (String fileName : fileNames) {
            ConfigProperties configProperties = new ConfigProperties(fileName,
                    contextClassLoader.getResourceAsStream(fileName));
            instance.propMap.put(fileName, configProperties);
        }
    }

    public static Configuration newInstance(String fileName, String... sources) throws IOException {
        if (fileName != null) {
            instance.setDefault(fileName);
            instance.propMap.clear();
            instance.add(fileName);
        }
        return instance;
    }

    public static Configuration newInstance(String[] fileNames, String... sources) throws IOException {
        if (fileNames != null && fileNames.length > 0) {
            instance.setDefault(fileNames[0]);
            instance.propMap.clear();
            instance.addAll(fileNames);
        }
        return instance;
    }

    public static Configuration getInstance() {
        return instance;
    }

    @Override
    public ConfigProperties getProperties() {
        return instance.defaultProp;
    }

    @Override
    public ConfigProperties getProperties(String fileName) {
        return instance.propMap.get(fileName);
    }

    @Override
    public Map<String, ConfigProperties> getAllProperties() {
        return instance.propMap;
    }

    @Override
    public ConfigClassLoader getClassLoader() {
        return classLoader;
    }
}
