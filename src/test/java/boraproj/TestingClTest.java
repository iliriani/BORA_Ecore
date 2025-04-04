package boraproj;

import junit.framework.TestCase;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestingClTest {

    @Test
    public void checkTaxedPrice(){
        var cl = new TestingCl();
        assertEquals(11,cl.getPriceWithTax(10),0.0001);
    }

    @Test
    public void checkTaxedPrice5(){
    var cl = new TestingCl();
    assertEquals(5.5,cl.getPriceWithTax(5),0.0001);
}
}