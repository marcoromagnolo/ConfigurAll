package configurall;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Marco Romagnolo
 */
public class ConfigContext {

    private final static Logger LOGGER = Logger.getLogger(ConfigContext.class.getName());

    private ConfigContext() {
    }

    public static <T> Class<T> getClass(Class<T> clazz) {
        String className = clazz.getName();
        Class<T> loadedClass = null;
        try {
            loadedClass = (Class<T>) ConfigFactory.getInstance().getClassLoader().loadClass(className);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return loadedClass;
    }

    public static Class getClass(String className) {
        Class loadedClass = null;
        try {
            loadedClass = ConfigFactory.getInstance().getClassLoader().getClass(className);
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return loadedClass;
    }

    public static <T> T newInstance(Class<T> clazz) {
        String className = clazz.getName();
        T obj = null;
        try {
            obj = (T) getClass(className).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return obj;
    }

    public static Object newInstance(String className) {
        Object obj = null;
        try {
            obj = getClass(className).newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return obj;
    }

    public static Object newInstance(Class clazz, Object... objects) {
        String className = clazz.getName();
        return newInstance(className, objects);
    }

    public static Object newInstance(String className, Object... objects) {
        Object obj = null;
        Class[] classes = new Class[objects.length];
        for (int i=0; i<objects.length; i++) {
            classes[i] = objects[i].getClass();
        }
        try {
            Constructor constructor = getClass(className).getConstructor(classes);
            obj = constructor.newInstance(objects);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return obj;
    }

    public static Object invoke(Class clazz, String methodName, Object... objects) {
        String className = clazz.getName();
        return invoke(className, methodName, objects);
    }

    public static Object invoke(String classname, String methodName, Object... objects) {
        Object obj = null;
        Class[] classes = new Class[objects.length];
        for (int i=0; i<objects.length; i++) {
            classes[i] = objects[i].getClass();
        }
        try {
            Method method = getClass(classname).getDeclaredMethod(methodName, classes);
            obj = method.invoke(objects);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            LOGGER.log(Level.SEVERE, e.getMessage(), e);
        }
        return obj;
    }

}
