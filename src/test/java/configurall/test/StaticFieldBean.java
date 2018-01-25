package configurall.test;

import configurall.Config;

/**
 * @author Marco Romagnolo
 */
public class StaticFieldBean {

    @Config("config.type.int")
    public static Integer INTEGER;

    public static Integer get() {
        return INTEGER;
    }
}
