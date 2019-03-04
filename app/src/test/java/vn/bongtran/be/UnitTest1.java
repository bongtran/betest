package vn.bongtran.be;


import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UnitTest1 {
    @Test
    public void generateMobile_isCorrect() {
        assertEquals(11, vn.bongtran.be.utils.Utils.generatePhoneNumber().length());
    }

    @Test
    public void generatePosition_isCorrect() {
        assertTrue(vn.bongtran.be.utils.BindingUtils.getCardPosition().length() > 0);
    }
}