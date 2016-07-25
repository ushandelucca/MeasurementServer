package de.oo2.tank.server.dao;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * This class creates the search query for the measurements based on the
 * given search criteria.
 */
public class MeasurementQueryComposer {
    private static final String PARAM_QUERY = "query";
    private static final String PARAM_RETURN = "return";
    private static final String PARAM_BEGIN = "begin";
    private static final String PARAM_END = "end";
    private static final String PARAM_SORT = "sort";
    private static final String PARAM_DATE_ASC = "+date";
    private static final String PARAM_DATE_DESC = "-date";
    private static final String PARAM_MAX_RESULT = "max_result";

    private SimpleDateFormat dateParser = new SimpleDateFormat("YYYY-MM-dd");

    private MultivaluedMap<String, String> criteria = new MultivaluedHashMap<String, String>();
    private String beginDate;
    private String endDate;
    private String sort;
    private Integer maxResult;

    /**
     * Constructor for the search criteria object.
     */
    public MeasurementQueryComposer() {
    }

    /**
     * Constructor for the search criteria object.
     *
     * @param criteria the search criteria
     * @throws MeasurementDataAccessException
     */
    public MeasurementQueryComposer(MultivaluedMap<String, String> criteria) throws MeasurementDataAccessException {
        this.criteria = criteria;
        isSyntaxCorrect();
    }

    /**
     * Set the search criteria.
     *
     * @param criteria the search criteria
     * @throws MeasurementDataAccessException
     */
    public void setCriteria(MultivaluedMap<String, String> criteria) throws MeasurementDataAccessException {
        this.criteria = criteria;
        isSyntaxCorrect();
    }

    /**
     * Returns the query string for the given search criteria.
     *
     * @return the query string
     */
    public String getQuery() {
        StringBuilder query = new StringBuilder();

        // { "timestamp": { $lt: new Date("2015-12-10T08:11:30.058Z") } }

        query.append("{ ");

        if ((beginDate != null) && (endDate != null)) {
            query.append("\"timestamp\": { $gt: new Date(\"" + beginDate + "\") }, \"timestamp\": { $lt: new Date(\"" + beginDate + "\") } ");
        }

        query.append(" }");

        return query.toString();
    }

    /**
     * Returns the sort string for the given search criteria.
     *
     * @return the sort string
     */
    public String getSort() throws MeasurementDataAccessException {
        StringBuilder sort = new StringBuilder();

        sort.append("{ ");

        if (PARAM_DATE_ASC.equals(this.sort)) {
            sort.append("\"timestamp\": 1 ");
        } else if (PARAM_DATE_DESC.equals(this.sort)) {
            sort.append("\"timestamp\": 0 ");
        }

        sort.append(" }");

        return sort.toString();
    }

    /**
     * Returns the limit parameter for the given search criteria.
     *
     * @return the limit string
     */
    public int getLimit() {
        if (maxResult != null) {
            return maxResult;
        }
        else {
            return 0;
        }
    }

    /**
     * Checks the syntax of the query map.
     *
     * @return <code>true</code> if the syntax is correct, otherwise <code>false</code>
     * @throws MeasurementDataAccessException
     */
    private boolean isSyntaxCorrect() throws MeasurementDataAccessException {
        // check the query parameter combination
        if (!criteria.containsKey(PARAM_QUERY)) {
            throw new MeasurementDataAccessException("Syntax Error in the search criterias: 'query' wrong format or not defined!");
        } else {
            if (!criteria.getFirst(PARAM_QUERY).equals(PARAM_RETURN)) {
                throw new MeasurementDataAccessException("Syntax Error in the search criteria: query without 'return'!");
            }
        }

        // check begin and end
        if (criteria.containsKey(PARAM_BEGIN)) {
            if (!criteria.containsKey(PARAM_END)) {
                throw new MeasurementDataAccessException("Syntax Error in the search criteria: 'begin' without 'end'!");
            }
            try {
                beginDate = criteria.getFirst(PARAM_BEGIN);
                dateParser.parse(beginDate); // check the format
            } catch (ParseException e) {
                beginDate = null;
                throw new MeasurementDataAccessException("Syntax Error in the search criteria: 'begin' wrong format!", e);
            }
        }

        if (criteria.containsKey(PARAM_END)) {
            if (!criteria.containsKey(PARAM_BEGIN)) {
                throw new MeasurementDataAccessException("Syntax Error in the search criteria: 'end' without 'begin'!");
            }
            try {
                endDate = criteria.getFirst(PARAM_END);
                dateParser.parse(endDate); // check the format
            } catch (ParseException e) {
                endDate = null;
                throw new MeasurementDataAccessException("Syntax Error in the search criteria: 'end' wrong 'format'!", e);
            }
        }

        // check sorting parameters
        if (criteria.containsKey(PARAM_SORT)) {

            sort = criteria.getFirst(PARAM_SORT);

            if ((!PARAM_DATE_ASC.equals(sort)) && (!PARAM_DATE_DESC.equals(sort))) {
                sort = null;
                throw new MeasurementDataAccessException("Syntax Error in the search criteria: 'sort' wrong format!");
            }
        }

        // check max_result
        if (criteria.containsKey(PARAM_MAX_RESULT)) {
            String number = criteria.getFirst(PARAM_MAX_RESULT);

            maxResult = Integer.parseInt(number);

            if (maxResult < 1) {
                maxResult = null;
                throw new MeasurementDataAccessException("Syntax Error in the search criteria: 'max_result' wrong format or not a positive number!");
            }
        }


        if ((beginDate == null) && (endDate == null) && (sort == null) && (maxResult == null)) {
            throw new MeasurementDataAccessException("No search criterias defined!");
        }

        return true;
    }

}
