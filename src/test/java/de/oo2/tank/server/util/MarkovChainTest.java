package de.oo2.tank.server.util;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MarkovChainTest {

    private MarkovChainTemperature mct = null;
    private MarkovChainWaterLevel mcw = null;

    @Before
    public void setUp() throws Exception {
        mct = new MarkovChainTemperature();
        mcw = new MarkovChainWaterLevel();
    }

    @After
    public void tearDown() throws Exception {
        mct = null;
        mcw = null;
    }

    @Test
    public void testGetTemperatures() throws Exception {
        Float[] temperatures = mct.getTemperatures();

        Assert.assertTrue(temperatures.length > 0);

//        for (Float temperature : temperatures) {
//            System.out.println(temperature);
//        }
    }


    @Test
    public void getWaterLevels() throws Exception {
        Float[] levels = mcw.getWaterLevels();

        Assert.assertTrue(levels.length > 0);

//        for (Float level : temperatures) {
//            System.out.println(level);
//        }
    }


}
