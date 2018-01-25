package configurall.test;

import configurall.ConfigContext;
import configurall.Configuration;
import configurall.ConfigFactory;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Marco Romagnolo
 */
public class TestStaticField extends AbstractTest  {

    @Before
    public void setUp() throws Exception {
        Configuration configuration = ConfigFactory.getInstance();
    }

    @Test
    @Override
    public void testStatics() {
        Integer res = (Integer) ConfigContext.invoke(StaticFieldBean.class, "get");
        Assert.assertEquals(res, new Integer(1000));
        super.testStatics();
    }
}
