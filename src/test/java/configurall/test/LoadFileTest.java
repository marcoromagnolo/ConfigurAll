package configurall.test;

import configurall.ConfigContext;
import configurall.ConfigFactory;
import configurall.ConfigProperties;
import configurall.Configuration;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Marco Romagnolo
 */
public class LoadFileTest extends AbstractTest {

    private static ConfigProperties myConfig;
    private static FieldTypeBean bean;

    @BeforeClass
    public static void setUp() throws IOException {
        Configuration configuration = ConfigFactory.newInstance("config.properties");
        myConfig = configuration.getProperties();
        Assert.assertNotNull(myConfig);
        bean = ConfigContext.newInstance(FieldTypeBean.class);
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
