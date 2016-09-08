package de.oo2.tank.server.persistence;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

/**
 * This class creates the search query for the measurements based on the
 * given search criteria.
 */
public class MongoQuery {
    private static final String PARAM_QUERY = "query";
    private static final String PARAM_RETURN = "return";
    private static final String PARAM_BEGIN = "begin";
    private static final String PARAM_END = "end";
    private static final String PARAM_SORT = "sort";
    private static final String PARAM_DATE_ASC = "+date";
    private static final String PARAM_DATE_DESC = "-date";
    private static final String PARAM_MAX_RESULT = "max_result";
    Map<String, String[]> queryParams = null;
    private SimpleDateFormat dateParser = new SimpleDateFormat("YYYY-MM-dd");
    private String beginDate;
    private String endDate;
    private String sort;
    private Integer maxResult;

    private boolean checked = false;

    /**
     * Constructor for the search criteria object.
     *
     * @param query the search query
     */
    public MongoQuery(Map<String, String[]> query) {
        queryParams = query;
    }

    /**
     * Returns the query string for the given search criteria.
     * { "timestamp": { $lt: new Date("2015-12-10T08:11:30.058Z") } }
     *
     * @return the query string
     * @throws PersistenceException in case of failure
     */
    public String getQuery() throws PersistenceException {
        checkQuery();

        StringBuilder query = new StringBuilder();

        query.append("{ ");

        if ((beginDate != null) && (endDate != null)) {
            query.append("\"timestamp\": { $gt: new Date(\"").append(beginDate).append("\") }, \"timestamp\": { $lt: new Date(\"").append(beginDate).append("\") } ");
        }

        query.append(" }");

        return query.toString();
    }

    /**
     * Returns the sort string for the given search criteria.
     *
     * @return the sort string
     * @throws PersistenceException in case of failure
     */
    public String getSort() throws PersistenceException {
        checkQuery();

        StringBuilder sortString = new StringBuilder();

        sortString.append("{ ");

        if (PARAM_DATE_ASC.equals(sort)) {
            sortString.append("\"timestamp\": 1 ");
        } else if (PARAM_DATE_DESC.equals(sort)) {
            sortString.append("\"timestamp\": -1 ");
        }

        sortString.append(" }");

        return sortString.toString();
    }

    /**
     * Returns the limit parameter for the given search criteria.
     *
     * @return the limit string
     * @throws PersistenceException in case of failure
     */
    public int getLimit() throws PersistenceException {
        checkQuery();

        if (maxResult != null) {
            return maxResult;
        }
        else {
            return 0;
        }
    }

    /**
     * Checks the syntax of the query.
     *
     * @return <code>true</code> if the syntax is correct, otherwise <code>false</code>
     * @throws PersistenceException in case of failure
     */
    private boolean checkQuery() throws PersistenceException {

        if (!checked) {

            if (queryParams.isEmpty()) {
                throw new PersistenceException("No search query defined!");
            }

            // check the query parameter combination
            if (queryParams.get(PARAM_QUERY) == null) {
                throw new PersistenceException("Syntax Error in the search criteria: 'query' wrong format or not defined!");
            } else {
                if (!queryParams.get(PARAM_QUERY)[0].equals(PARAM_RETURN)) {
                    throw new PersistenceException("Syntax Error in the search criteria: query without 'return'!");
                }
            }

            // check begin and end
            if (queryParams.get(PARAM_BEGIN) != null) {
                if (queryParams.get(PARAM_END) == null) {
                    throw new PersistenceException("Syntax Error in the search criteria: 'begin' without 'end'!");
                }
                try {
                    beginDate = queryParams.get(PARAM_BEGIN)[0];
                    dateParser.parse(beginDate); // check the format
                } catch (ParseException e) {
                    beginDate = null;
                    throw new PersistenceException("Syntax Error in the search criteria: 'begin' wrong format!", e);
                }
            }

            if (queryParams.get(PARAM_END) != null) {
                if (queryParams.get(PARAM_BEGIN) == null) {
                    throw new PersistenceException("Syntax Error in the search criteria: 'end' without 'begin'!");
                }
                try {
                    endDate = queryParams.get(PARAM_END)[0];
                    dateParser.parse(endDate); // check the format
                } catch (ParseException e) {
                    endDate = null;
                    throw new PersistenceException("Syntax Error in the search criteria: 'end' wrong 'format'!", e);
                }
            }

            // check sorting parameters
            if (queryParams.get(PARAM_SORT) != null) {

                sort = queryParams.get(PARAM_SORT)[0];

                if ((!PARAM_DATE_ASC.equals(sort)) && (!PARAM_DATE_DESC.equals(sort))) {
                    sort = null;
                    throw new PersistenceException("Syntax Error in the search criteria: 'sort' wrong format!");
                }
            }

            // check max_result
            if (queryParams.get(PARAM_MAX_RESULT) != null) {
                String number = queryParams.get(PARAM_MAX_RESULT)[0];

                maxResult = Integer.parseInt(number);

                if (maxResult < 1) {
                    maxResult = null;
                    throw new PersistenceException("Syntax Error in the search criteria: 'max_result' wrong format or not a positive number!");
                }
            }

            if ((beginDate == null) && (endDate == null) && (sort == null) && (maxResult == null)) {
                throw new PersistenceException("No search criterias defined!");
            }

            checked = true;
        }

        return checked;
    }

}
