package de.oo2.tank.server.persistence;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Map;

/**
 * This class parses the search query for the measurements and provides the criteria as a members and methods.
 */
public class QueryParser {
    private static final String PARAM_QUERY = "query";
    private static final String PARAM_RETURN = "return";
    private static final String PARAM_SENSOR = "sensor";
    private static final String PARAM_BEGIN = "begin";
    private static final String PARAM_END = "end";
    private static final String PARAM_SORT = "sort";
    private static final String PARAM_DATE_ASC = "+date";
    private static final String PARAM_DATE_DESC = "-date";
    private static final String PARAM_MAX_RESULT = "max_result";

    private DateTimeFormatter dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd");

    private Map<String, String[]> queryParams = null;
    private boolean checked = false;

    private String command = null;

    private String sensor = null;

    private DateTime beginDate = null;
    private DateTime endDate = null;

    private boolean sortDateAsc = false;
    private boolean sortDateDesc = false;

    private int limit;

    /**
     * Sets the query parameters.
     *
     * @param query the search query
     */
    public void setQuery(Map<String, String[]> query) {
        queryParams = query;
    }

    /**
     * Checks the syntax of the query.
     *
     * @return <code>true</code> if the syntax is correct, otherwise <code>false</code>
     * @throws PersistenceException in case of failure
     */
    boolean checkQuery() throws PersistenceException {

        if (!checked) {

            if (queryParams.isEmpty()) {
                throw new PersistenceException("No search query defined!");
            }

            // check the query parameter combination
            if (queryParams.get(PARAM_QUERY) == null) {
                throw new PersistenceException("Syntax Error in the search criteria: 'query' wrong format or not defined!");
            } else {
                if (queryParams.get(PARAM_QUERY)[0].equals(PARAM_RETURN)) {
                    command = queryParams.get(PARAM_QUERY)[0];
                } else {
                    throw new PersistenceException("Syntax Error in the search criteria: query without 'return'!");
                }
            }

            // check sensor
            if (queryParams.get(PARAM_SENSOR) != null) {

                sensor = queryParams.get(PARAM_SENSOR)[0];

                if (sensor.length() > 12) {
                    throw new PersistenceException("Syntax Error in the search criteria: 'sensor' wrong format!");
                }
            }

            // check begin and end
            if (queryParams.get(PARAM_BEGIN) != null) {
                if (queryParams.get(PARAM_END) == null) {
                    throw new PersistenceException("Syntax Error in the search criteria: 'begin' without 'end'!");
                }
                try {
                    beginDate = DateTime.parse(queryParams.get(PARAM_BEGIN)[0], dateFormat);
                } catch (Exception e) {
                    beginDate = null;
                    throw new PersistenceException("Syntax Error in the search criteria: 'begin' wrong format!", e);
                }
            }

            if (queryParams.get(PARAM_END) != null) {
                if (queryParams.get(PARAM_BEGIN) == null) {
                    throw new PersistenceException("Syntax Error in the search criteria: 'end' without 'begin'!");
                }
                try {
                    endDate = DateTime.parse(queryParams.get(PARAM_END)[0], dateFormat);
                } catch (Exception e) {
                    endDate = null;
                    throw new PersistenceException("Syntax Error in the search criteria: 'end' wrong 'format'!", e);
                }
            }

            // check sorting parameters
            if (queryParams.get(PARAM_SORT) != null) {

                String sort = queryParams.get(PARAM_SORT)[0];
                sortDateAsc = PARAM_DATE_ASC.equals(sort);
                sortDateDesc = PARAM_DATE_DESC.equals(sort);

                if (!sortDateAsc && !sortDateDesc) {
                    throw new PersistenceException("Syntax Error in the search criteria: 'sort' wrong format!");
                }
            }

            // check max_result
            if (queryParams.get(PARAM_MAX_RESULT) != null) {
                String number = queryParams.get(PARAM_MAX_RESULT)[0];

                limit = Integer.parseInt(number);

                if (limit < 1) {
                    limit = 0;
                    throw new PersistenceException("Syntax Error in the search criteria: 'max_result' wrong format or not a positive number!");
                }
            }

            if ((beginDate == null) && (endDate == null) && (!hasSensor()) && (!hasSort()) && (limit == 0)) {
                throw new PersistenceException("No search criteria defined!");
            }

            checked = true;
        }

        return true;
    }

    /**
     * Returns <code>true</code> if the query contains a valid query command.
     *
     * @return <code>true</code> if the query contains a valid query command
     */
    boolean hasCommand() {
        return !"".equals(command);
    }

    /**
     * Returns the query command.
     *
     * @return the query command
     */
    String getCommand() {
        return command;
    }

    /**
     * Returns <code>true</code> if the query contains a valid sensor name.
     *
     * @return <code>true</code> if the query contains a valid sensor name
     */
    boolean hasSensor() {
        return sensor != null;
    }

    /**
     * Returns the sensor name.
     *
     * @return the sensor name
     */
    public String getSensor() {
        return sensor;
    }

    /**
     * Returns <code>true</code> if the query contains a valid begin and end date.
     *
     * @return <code>true</code> if the query contains a valid begin and end date
     */
    boolean hasDate() {
        return (beginDate != null) || (endDate != null);
    }

    /**
     * Returns the begin date.
     *
     * @return the begin date.
     */
    DateTime getBeginDate() {
        return beginDate;
    }

    /**
     * Returns the end date.
     *
     * @return the end date.
     */
    DateTime getEndDate() {
        return endDate;
    }

    /**
     * Returns <code>true</code> if the query contains a valid sort parameter.
     *
     * @return <code>true</code> if the query contains a valid sort parameter
     */
    boolean hasSort() {
        return sortDateAsc || sortDateDesc;
    }

    /**
     * Returns <code>true</code> if the query contains a date asc command.
     *
     * @return <code>true</code> if the query contains a date asc command
     */
    boolean isSortDateAsc() {
        return sortDateAsc;
    }

    /**
     * Returns <code>true</code> if the query contains a date desc command.
     *
     * @return <code>true</code> if the query contains a date desc command
     */
    boolean isSortDateDesc() {
        return sortDateDesc;
    }

    /**
     * Returns <code>true</code> if the query contains a limit for the result.
     *
     * @return <code>true</code> if the query contains a limit for the result
     */
    boolean hasLimit() {
        return limit > 0;
    }

    /**
     * Returns the limit for the result.
     *
     * @return the limit for the result.
     */
    int getLimit() {
        return limit;
    }
}


