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

    private MultivaluedMap<String, String> criteria = new MultivaluedHashMap<>();
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
     */
    public MeasurementQueryComposer(MultivaluedMap<String, String> criteria) {
        this.criteria = criteria;
    }

    /**
     * Set the search criteria.
     *
     * @param criteria the search criteria
     */
    public void setCriteria(MultivaluedMap<String, String> criteria) {
        this.criteria = criteria;
    }

    /**
     * Returns the SQL search query for the given search criteria.
     *
     * @return the SQL query
     * @throws MeasurementDataAccessException
     */
    public String getSearchQuery() throws MeasurementDataAccessException {

        isQuerySyntaxCorrect();

        StringBuilder query = new StringBuilder();

        query.append("SELECT * FROM measurement ");

        if ((beginDate != null) && (endDate != null)) {
            query.append("WHERE ");
            query.append("datetime > '" + beginDate + "' and datetime < '" + endDate + "' ");
        }

        if (PARAM_DATE_ASC.equals(sort)) {
            query.append("order by date(datetime) ASC ");
        } else if (PARAM_DATE_DESC.equals(sort)) {
            query.append("order by date(datetime) DESC ");
        }

        if (maxResult != null) {
            query.append("LIMIT " + maxResult);
        }

        query.append(" ;");


        return query.toString();
    }

    /**
     * Checks the syntax of the query map.
     *
     * @return <code>true</code> if the syntax is correct, otherwise <code>false</code>
     * @throws MeasurementDataAccessException
     */
    private boolean isQuerySyntaxCorrect() throws MeasurementDataAccessException {
        // check the query parameter combination
        if (!criteria.containsKey(PARAM_QUERY)) {
            return false;
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

        return true;
    }

}
