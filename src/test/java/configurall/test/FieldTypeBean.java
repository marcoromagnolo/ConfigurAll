package configurall.test;

import configurall.Config;

/**
 * @author Marco Romagnolo
 */
public class FieldTypeBean {

    @Config("config.type.boolean")
    public boolean booleanField;

    @Config("config.type.boolean")
    private Boolean bigBooleanField;

    @Config("config.type.boolean")
    public static boolean BOOLEAN;

    @Config("config.type.boolean")
    public static Boolean BIGBOOLEAN;

    @Config("config.type.byte")
    private byte byteField;

    @Config("config.type.byte")
    private Byte bigByteField;

    @Config("config.type.byte")
    public static byte BYTE;

    @Config("config.type.byte")
    public static Byte BIGBYTE;

    @Config("config.type.char")
    private char charField;

    @Config("config.type.char")
    private Character characterField;

    @Config("config.type.char")
    public static char CHAR;

    @Config("config.type.char")
    public static Character CHARACTER;

    @Config("config.type.int")
    private int intField;

    @Config("config.type.int")
    private Integer integerField;

    @Config("config.type.int")
    public static int INT;

    @Config("config.type.int")
    public static Integer INTEGER;

    @Config("config.type.long")
    private long longField;

    @Config("config.type.long")
    private Long bigLongField;

    @Config("config.type.long")
    public static long LONG;

    @Config("config.type.long")
    public static Long BIGLONG;

    @Config("config.type.float")
    private float floatField;

    @Config("config.type.float")
    private Float bigFloatField;

    @Config("config.type.float")
    public static float FLOAT;

    @Config("config.type.float")
    public static Float BIGFLOAT;

    @Config("config.type.double")
    private double doubleField;

    @Config("config.type.double")
    private Double bigDoubleField;

    @Config("config.type.double")
    public static double DOUBLE;

    @Config("config.type.double")
    public static Double BIGDOUBLE;

    @Config("config.type.string")
    private String stringField;

    @Config("config.type.string")
    public static String STRING;

    public Boolean getBigBooleanField() {
        return bigBooleanField;
    }

    public boolean isBooleanField() {
        return booleanField;
    }

    public byte getByteField() {
        return byteField;
    }

    public Byte getBigByteField() {
        return bigByteField;
    }

    public char getCharField() {
        return charField;
    }

    public Character getCharacterField() {
        return characterField;
    }

    public int getIntField() {
        return intField;
    }

    public Integer getIntegerField() {
        return integerField;
    }

    public long getLongField() {
        return longField;
    }

    public Long getBigLongField() {
        return bigLongField;
    }

    public float getFloatField() {
        return floatField;
    }

    public Float getBigFloatField() {
        return bigFloatField;
    }

    public double getDoubleField() {
        return doubleField;
    }

    public Double getBigDoubleField() {
        return bigDoubleField;
    }

    public String getStringField() {
        return stringField;
    }

//    @Override
//    public String toString() {
//        return "FieldTypeBean{" +
//                "booleanField=" + booleanField +
//                ", bigBooleanField=" + bigBooleanField +
//                ", byteField=" + byteField +
//                ", bigByteField=" + bigByteField +
//                ", charField=" + charField +
//                ", characterField=" + characterField +
//                ", intField=" + intField +
//                ", integerField=" + integerField +
//                ", longField=" + longField +
//                ", bigLongField=" + bigLongField +
//                ", floatField=" + floatField +
//                ", bigFloatField=" + bigFloatField +
//                ", doubleField=" + doubleField +
//                ", bigDoubleField=" + bigDoubleField +
//                ", stringField='" + stringField + '\'' +
//                '}';
//    }
}
