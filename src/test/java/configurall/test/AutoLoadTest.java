package configurall.test;

import configurall.ConfigFactory;
import configurall.ConfigContext;
import configurall.ConfigProperties;
import configurall.Configuration;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Marco Romagnolo
 */
public class AutoLoadTest extends AbstractTest {

    private static ConfigProperties myConfig;
    private static FieldTypeBean bean;

    @BeforeClass
    public static void setUp() throws IOException, IllegalAccessException, InstantiationException {
        Configuration configuration = ConfigFactory.getInstance();
        myConfig = configuration.getProperties();
        Assert.assertNotNull(myConfig);
        bean = ConfigContext.newInstance(FieldTypeBean.class);
    }

    @Test
    public void testClass() throws IOException, IllegalAccessException, InstantiationException {
        Class<FieldTypeBean> clazz = ConfigContext.getClass(FieldTypeBean.class);
        Assert.assertNotNull(clazz);
        Object obj = clazz.newInstance();
        Assert.assertNotNull(obj);
    }

    @Test
    public void testProperties() {
        System.out.print(myConfig.getPropertiesAsString());
    }

    @Test
    public void testStatics() {
        super.testStatics();
    }

    @Test
    public void testFields() {
        super.testFields(bean);
    }

}
