package configurall.test;

import configurall.ConfigContext;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

/**
 * @author Marco Romagnolo
 */
public class BeanOfBeanTest extends AbstractTest {

    private static BeanOfBean bean;

    @BeforeClass
    public static void setUp() throws IOException, IllegalAccessException, InstantiationException {
        bean = ConfigContext.newInstance(BeanOfBean.class);
    }

    @Test
    public void testStatics() {
        super.testStatics();
    }

    @Test
    public void testFields() {
        super.testFields(bean.getBean());
    }
}
