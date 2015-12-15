package de.oo2.tank.server.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static de.oo2.tank.server.MeasurementFixture.*;
import static de.oo2.tank.server.QueryFixture.*;

public class MeasurementQueryComposerTest {
    private MeasurementQueryComposer composer;

    @Before
    public void setUp() throws Exception {
        composer = new MeasurementQueryComposer();
    }

    @After
    public void tearDown() throws Exception {
        composer = null;
    }

    @Test
    public void testGetQuery1() throws Exception {
        composer.setCriteria(QUERY_MAP_1);
        String query = composer.getSearchQuery();
        System.out.println(query);
        Assert.assertEquals("SELECT * FROM measurement WHERE datetime > '2013-01-13' and datetime < '2014-01-20' order by date(datetime) DESC LIMIT 10 ;", query);
    }

    @Test
    public void testGetQuery2() throws Exception {
        composer.setCriteria(QUERY_MAP_2);
        String query = composer.getSearchQuery();
        System.out.println(query);
        Assert.assertEquals("SELECT * FROM measurement WHERE datetime > '2013-01-13' and datetime < '2014-01-20' order by date(datetime) ASC LIMIT 20 ;", query);
    }

    @Test
    public void testGetQuery3() throws Exception {
        composer.setCriteria(QUERY_MAP_3);
        String query = composer.getSearchQuery();
        System.out.println(query);
        Assert.assertEquals("SELECT * FROM measurement LIMIT 30 ;", query);
    }
}
