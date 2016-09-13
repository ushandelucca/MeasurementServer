package de.oo2.tank.server.persistence;

import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Test for the query parser.
 */
public class QueryParserTest {
    private QueryParser parser = new QueryParser();
    private Map<String, String[]> query;

    @Before
    public void setUp() {
        query = new HashMap<>();
    }

    @After
    public void tearDown() {
        query = null;
    }

    @Test
    public void testDesc() {
        // query=return&begin=2013-01-13&end=2014-01-20&sort=-date&max_result=10

        query.put("query", new String[]{"return"});
        query.put("begin", new String[]{"2013-01-13"});
        query.put("end", new String[]{"2014-01-20"});
        query.put("sort", new String[]{"-date"});
        query.put("max_result", new String[]{"10"});

        parser.setQuery(query);
        try {
            parser.checkQuery();
        } catch (PersistenceException e) {
            Assert.fail();
        }

        Assert.assertTrue(parser.hasCommand());
        Assert.assertEquals("return", parser.getCommand());

        Assert.assertTrue(parser.hasDate());
        Assert.assertEquals(new DateTime(2013, 1, 13, 0, 0), parser.getBeginDate());
        Assert.assertEquals(new DateTime(2014, 1, 20, 0, 0), parser.getEndDate());

        Assert.assertTrue(parser.hasSort());
        Assert.assertFalse(parser.isSortDateAsc());
        Assert.assertTrue(parser.isSortDateDesc());

        Assert.assertTrue(parser.hasLimit());
        Assert.assertEquals(10, parser.getLimit());
    }

    @Test
    public void testAsc() {
        // query=return&begin=2013-01-13&end=2014-01-20&sort=+date&max_result=20

        query.put("query", new String[]{"return"});
        query.put("begin", new String[]{"2013-01-13"});
        query.put("end", new String[]{"2014-01-20"});
        query.put("sort", new String[]{"+date"});
        query.put("max_result", new String[]{"20"});

        parser.setQuery(query);
        try {
            parser.checkQuery();
        } catch (PersistenceException e) {
            Assert.fail();
        }

        Assert.assertTrue(parser.hasCommand());
        Assert.assertEquals("return", parser.getCommand());

        Assert.assertTrue(parser.hasDate());
        Assert.assertEquals(new DateTime(2013, 1, 13, 0, 0), parser.getBeginDate());
        Assert.assertEquals(new DateTime(2014, 1, 20, 0, 0), parser.getEndDate());

        Assert.assertTrue(parser.hasSort());
        Assert.assertTrue(parser.isSortDateAsc());
        Assert.assertFalse(parser.isSortDateDesc());

        Assert.assertTrue(parser.hasLimit());
        Assert.assertEquals(20, parser.getLimit());
    }

    @Test
    public void testLimit() throws Exception {
        // query=return&max_result=30

        query.put("query", new String[]{"return"});
        query.put("max_result", new String[]{"30"});

        parser.setQuery(query);
        try {
            parser.checkQuery();
        } catch (PersistenceException e) {
            Assert.fail();
        }

        Assert.assertTrue(parser.hasCommand());
        Assert.assertEquals("return", parser.getCommand());

        Assert.assertFalse(parser.hasDate());

        Assert.assertFalse(parser.hasSort());

        Assert.assertTrue(parser.hasLimit());
        Assert.assertEquals(30, parser.getLimit());
    }

    @Test
    public void testEmptyQuery() {

        parser.setQuery(query);
        try {
            parser.checkQuery();
        } catch (PersistenceException e) {
            Assert.assertTrue(e.getMessage().contains("No search query"));
            return;
        }

        Assert.fail();
    }

    @Test
    public void testWrongCommand() {
        // query=return&begin=2013-01-13&end=2014-01-20&sort=-date&max_result=10

        query.put("query", new String[]{"wrong"});
        query.put("begin", new String[]{"2013-01-13"});
        query.put("end", new String[]{"2014-01-20"});
        query.put("sort", new String[]{"-date"});
        query.put("max_result", new String[]{"10"});

        parser.setQuery(query);
        try {
            parser.checkQuery();
        } catch (PersistenceException e) {
            Assert.assertTrue(e.getMessage().contains("query without 'return'"));
            return;
        }

        Assert.fail();
    }

    @Test
    public void testWrongBeginDate() {
        // query=return&begin=20130113&end=2014-01-20&sort=-date&max_result=10

        query.put("query", new String[]{"return"});
        query.put("begin", new String[]{"20130113"});
        query.put("end", new String[]{"2014-01-20"});
        query.put("sort", new String[]{"-date"});
        query.put("max_result", new String[]{"10"});

        parser.setQuery(query);
        try {
            parser.checkQuery();
        } catch (PersistenceException e) {
            Assert.assertTrue(e.getMessage().contains("'begin'"));
            return;
        }

        Assert.fail();
    }

    @Test
    public void testWrongEndDate() {
        // query=return&begin=2013-01-13&end=20140120&sort=+date&max_result=20

        query.put("query", new String[]{"return"});
        query.put("begin", new String[]{"2013-01-13"});
        query.put("end", new String[]{"20140120"});
        query.put("sort", new String[]{"-date"});
        query.put("max_result", new String[]{"10"});

        parser.setQuery(query);
        try {
            parser.checkQuery();
        } catch (PersistenceException e) {
            Assert.assertTrue(e.getMessage().contains("'end'"));
            return;
        }

        Assert.fail();
    }

    @Test
    public void testLimitTooSmall() {
        // query=return&max_result=0

        query.put("query", new String[]{"return"});
        query.put("max_result", new String[]{"0"});

        parser.setQuery(query);
        try {
            parser.checkQuery();
        } catch (PersistenceException e) {
            Assert.assertTrue(e.getMessage().contains("'max_result' wrong format or not a positive number"));
            return;
        }

        Assert.fail();
    }

    @Test
    public void testWrongQuery() {
        query.put("wrong", new String[]{"return"});
        query.put("max_result", new String[]{"10"});

        parser.setQuery(query);
        try {
            parser.checkQuery();
        } catch (PersistenceException e) {
            Assert.assertTrue(e.getMessage().contains(" 'query' wrong format or not defined!"));
            return;
        }

        Assert.fail();
    }

    @Test
    public void testBeginWithoutEnd() {
        query.put("query", new String[]{"return"});
        query.put("begin", new String[]{"2015-01-20"});

        parser.setQuery(query);
        try {
            parser.checkQuery();
        } catch (PersistenceException e) {
            Assert.assertTrue(e.getMessage().contains("'begin' without 'end'!"));
            return;
        }

        Assert.fail();
    }

    @Test
    public void testEndWithoutBegin() {
        query.put("query", new String[]{"return"});
        query.put("end", new String[]{"2012-01-13"});

        parser.setQuery(query);
        try {
            parser.checkQuery();
        } catch (PersistenceException e) {
            Assert.assertTrue(e.getMessage().contains("end' without 'begin'!"));
            return;
        }

        Assert.fail();
    }

    @Test
    public void testOnlyBla() {
        query.put("query", new String[]{"return"});

        parser.setQuery(query);
        try {
            parser.checkQuery();
        } catch (PersistenceException e) {
            Assert.assertTrue(e.getMessage().contains("No search criteria defined!"));
            return;
        }

        Assert.fail();
    }

    @Test
    public void testWrongSort() {
        query.put("query", new String[]{"return"});
        query.put("sort", new String[]{"wrong"});

        parser.setQuery(query);
        try {
            parser.checkQuery();
        } catch (PersistenceException e) {
            Assert.assertTrue(e.getMessage().contains("'sort' wrong format!"));
            return;
        }

        Assert.fail();
    }
}
