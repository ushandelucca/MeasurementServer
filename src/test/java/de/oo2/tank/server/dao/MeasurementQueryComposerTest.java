package de.oo2.tank.server.dao;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static de.oo2.tank.QueryFixture.*;

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
        String query = composer.getQuery();
        System.out.println(query);
        Assert.assertEquals("{ \"timestamp\": { $gt: new Date(\"2013-01-13\") }, \"timestamp\": { $lt: new Date(\"2013-01-13\") }  }", query);

        String sort = composer.getSort();
        System.out.println(sort);
        Assert.assertEquals("{ \"timestamp\": 0  }", sort);

        int limit = composer.getLimit();
        System.out.println(limit);
        Assert.assertEquals(10, limit);
    }

    @Test
    public void testGetQuery2() throws Exception {
        composer.setCriteria(QUERY_MAP_2);
        String query = composer.getQuery();
        System.out.println(query);
        Assert.assertEquals("{ \"timestamp\": { $gt: new Date(\"2013-01-13\") }, \"timestamp\": { $lt: new Date(\"2013-01-13\") }  }", query);

        String sort = composer.getSort();
        System.out.println(sort);
        Assert.assertEquals("{ \"timestamp\": 1  }", sort);

        int limit = composer.getLimit();
        System.out.println(limit);
        Assert.assertEquals(20, limit);
    }

    @Test
    public void testGetQuery3() throws Exception {
        composer.setCriteria(QUERY_MAP_3);
        String query = composer.getQuery();
        System.out.println(query);
        Assert.assertEquals("{  }", query);

        String sort = composer.getSort();
        System.out.println(sort);
        Assert.assertEquals("{  }", sort);

        int limit = composer.getLimit();
        System.out.println(limit);
        Assert.assertEquals(30, limit);
    }
}
