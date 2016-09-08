package de.oo2.tank.server.persistence;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Test for the query creation.
 */
public class MongoQueryTest {
    private MongoQuery composer;
    private Map<String, String[]> params;

    @Before
    public void setUp() throws Exception {
        params = new HashMap<>();
    }

    @After
    public void tearDown() throws Exception {
        composer = null;
        params = null;
    }

    @Test
    public void testGetQuery1() throws Exception {
        // query=return&begin=2013-01-13&end=2014-01-20&sort=-date&max_result=10

        params.put("query", new String[]{"return"});
        params.put("begin", new String[]{"2013-01-13"});
        params.put("end", new String[]{"2014-01-20"});
        params.put("sort", new String[]{"-date"});
        params.put("max_result", new String[]{"10"});

        composer = new MongoQuery(params);

        String query = composer.getQuery();
        System.out.println(query);
        Assert.assertEquals("{ \"timestamp\": { $gt: new Date(\"2013-01-13\") }, \"timestamp\": { $lt: new Date(\"2013-01-13\") }  }", query);

        String sort = composer.getSort();
        System.out.println(sort);
        Assert.assertEquals("{ \"timestamp\": -1  }", sort);

        int limit = composer.getLimit();
        System.out.println(limit);
        Assert.assertEquals(10, limit);
    }

    @Test
    public void testGetQuery2() throws Exception {
        // query=return&begin=2013-01-13&end=2014-01-20&sort=+date&max_result=20

        params.put("query", new String[]{"return"});
        params.put("begin", new String[]{"2013-01-13"});
        params.put("end", new String[]{"2014-01-20"});
        params.put("sort", new String[]{"+date"});
        params.put("max_result", new String[]{"20"});

        composer = new MongoQuery(params);

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
        // query=return&max_result=30

        params.put("query", new String[]{"return"});
        params.put("max_result", new String[]{"30"});

        composer = new MongoQuery(params);

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
