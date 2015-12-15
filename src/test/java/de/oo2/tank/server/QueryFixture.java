package de.oo2.tank.server;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Arrays;
import java.util.Date;

/**
 * Fixture for the unit tests.
 */
public class QueryFixture {

    public static final MultivaluedMap<String, String> QUERY_MAP_1;
    public static final MultivaluedMap<String, String> QUERY_MAP_2;
    public static final MultivaluedMap<String, String> QUERY_MAP_3;

    static {
        // according to the URL .../temperatures?query=return&begin=2013-01-13&end=2014-01-20&sort=-date&max_result=10
        QUERY_MAP_1 = new MultivaluedHashMap<>();
        QUERY_MAP_1.put("query", Arrays.asList("return"));
        QUERY_MAP_1.put("end", Arrays.asList("2014-01-20"));
        QUERY_MAP_1.put("begin", Arrays.asList("2013-01-13"));
        QUERY_MAP_1.put("sort", Arrays.asList("-date"));
        QUERY_MAP_1.put("max_result", Arrays.asList("10"));

        // according to the URL .../temperatures?query=return&begin=2013-01-13&end=2014-01-20&sort=+date&max_result=20
        QUERY_MAP_2 = new MultivaluedHashMap<>();
        QUERY_MAP_2.put("query", Arrays.asList("return"));
        QUERY_MAP_2.put("end", Arrays.asList("2014-01-20"));
        QUERY_MAP_2.put("begin", Arrays.asList("2013-01-13"));
        QUERY_MAP_2.put("sort", Arrays.asList("+date"));
        QUERY_MAP_2.put("max_result", Arrays.asList("20"));

        // according to the URL .../temperatures?query=return&&max_result=30
        QUERY_MAP_3 = new MultivaluedHashMap<>();
        QUERY_MAP_3.put("query", Arrays.asList("return"));
        QUERY_MAP_3.put("max_result", Arrays.asList("30"));

    }

}
