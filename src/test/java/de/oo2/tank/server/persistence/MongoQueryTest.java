package de.oo2.tank.server.persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test for the query creation.
 */
public class MongoQueryTest {
    private MongoQuery composer;

    @Before
    public void setUp() throws Exception {
        // nothing to do
    }

    @After
    public void tearDown() throws Exception {
        composer = null;
    }

    @Test
    public void testGetQuery1() throws Exception {
        composer = new MongoQuery("query=return&begin=2013-01-13&end=2014-01-20&sort=-date&max_result=10");

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
        composer = new MongoQuery("query=return&begin=2013-01-13&end=2014-01-20&sort=+date&max_result=20");

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
        composer = new MongoQuery("query=return&max_result=30");

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