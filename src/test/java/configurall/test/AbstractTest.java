package configurall.test;

import org.junit.Assert;

/**
 * @author Marco Romagnolo
 */
public class AbstractTest {

    public void testStatics() {
        System.out.println("Static BOOLEAN: " + FieldTypeBean.BOOLEAN);
        System.out.println("Static BYTE: " + FieldTypeBean.BYTE);
        System.out.println("Static CHAR: " + FieldTypeBean.CHAR);
        System.out.println("Static INT: " + FieldTypeBean.INT);
        System.out.println("Static LONG: " + FieldTypeBean.LONG);
        System.out.println("Static FLOAT: " + FieldTypeBean.FLOAT);
        System.out.println("Static DOUBLE: " + FieldTypeBean.DOUBLE);

        System.out.println("Static BIGBOOLEAN: " + FieldTypeBean.BIGBOOLEAN);
        System.out.println("Static BIGBYTE: " + FieldTypeBean.BIGBYTE);
        System.out.println("Static CHARACTER: " + FieldTypeBean.CHARACTER);
        System.out.println("Static INTEGER: " + FieldTypeBean.INTEGER);
        System.out.println("Static BIGLONG: " + FieldTypeBean.BIGLONG);
        System.out.println("Static BIGFLOAT: " + FieldTypeBean.BIGFLOAT);
        System.out.println("Static BIGDOUBLE: " + FieldTypeBean.BIGDOUBLE);
        System.out.println("Static STRING: " + FieldTypeBean.STRING);

        Assert.assertTrue(FieldTypeBean.BOOLEAN);
        Assert.assertTrue(FieldTypeBean.BIGBOOLEAN);
        Assert.assertTrue(FieldTypeBean.CHAR == 'a');
        Assert.assertTrue(FieldTypeBean.CHARACTER == 'a');
        Assert.assertTrue(FieldTypeBean.BYTE == 100);
        Assert.assertTrue(FieldTypeBean.BIGBYTE == 100);
        Assert.assertTrue(FieldTypeBean.INT == 1000);
        Assert.assertTrue(FieldTypeBean.INTEGER == 1000);
        Assert.assertTrue(FieldTypeBean.LONG == 10000L);
        Assert.assertTrue(FieldTypeBean.BIGLONG == 10000L);
        Assert.assertTrue(FieldTypeBean.FLOAT == 3.24f);
        Assert.assertTrue(FieldTypeBean.BIGFLOAT == 3.24f);
        Assert.assertTrue(FieldTypeBean.DOUBLE == 3.24123d);
        Assert.assertTrue(FieldTypeBean.BIGDOUBLE == 3.24123d);
        Assert.assertTrue(FieldTypeBean.STRING.equals("Is String"));
    }

    public void testFields(FieldTypeBean app) {

        System.out.println("Field booleanField: " + app.isBooleanField());
        System.out.println("Field byteField: " + app.getByteField());
        System.out.println("Field charField: " + app.getCharField());
        System.out.println("Field intField: " + app.getIntField());
        System.out.println("Field longField: " + app.getLongField());
        System.out.println("Field floatField: " + app.getFloatField());
        System.out.println("Field doubleField: " + app.getDoubleField());

        System.out.println("Field bigBooleanField: " + app.getBigBooleanField());
        System.out.println("Field bigByteField: " + app.getBigByteField());
        System.out.println("Field characterField: " + app.getCharacterField());
        System.out.println("Field integerField: " + app.getIntegerField());
        System.out.println("Field bigLongField: " + app.getBigLongField());
        System.out.println("Field bigFloatField: " + app.getBigFloatField());
        System.out.println("Field biDoubleField: " + app.getBigDoubleField());
        System.out.println("Field stringField: " + app.getStringField());

        Assert.assertTrue(app.isBooleanField());
        Assert.assertTrue(app.getCharField() == 'a');
        Assert.assertTrue(app.getByteField() == 100);
        Assert.assertTrue(app.getIntField() == 1000);
        Assert.assertTrue(app.getLongField() == 10000L);
        Assert.assertTrue(app.getFloatField() == 3.24f);
        Assert.assertTrue(app.getDoubleField() == 3.24123d);

        Assert.assertTrue(app.getBigBooleanField());
        Assert.assertTrue(app.getCharacterField() == 'a');
        Assert.assertTrue(app.getBigByteField() == 100);
        Assert.assertTrue(app.getIntegerField() == 1000);
        Assert.assertTrue(app.getBigLongField() == 10000L);
        Assert.assertTrue(app.getBigFloatField() == 3.24f);
        Assert.assertTrue(app.getBigDoubleField() == 3.24123d);
        Assert.assertTrue(app.getStringField().equals("Is String"));
    }
}
