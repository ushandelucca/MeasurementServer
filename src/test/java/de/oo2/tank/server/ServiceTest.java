package de.oo2.tank.server;

import de.oo2.tank.model.Measurement;
import de.oo2.tank.server.dao.MeasurementDataAccessException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.PathSegment;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.util.List;

import static de.oo2.tank.QueryFixture.QUERY_MAP_3;
import static de.oo2.tank.QueryFixture.QUERY_MAP_4;
import static de.oo2.tank.model.MeasurementFixture.MEASUREMENT_1;


public class ServiceTest {
    private WaterRessource ressource;

    @Before
    public void setUp() throws Exception {
        ressource = new WaterRessource();
    }

    @After
    public void tearDown() throws Exception {
        ressource = null;
    }

    @Test
    public void testCreateMeasurement() throws Exception {
        Measurement measurement = ressource.createMeasurement(MEASUREMENT_1);
        Assert.assertNotNull(measurement.getId());
    }

    @Test
    public void testGetTemperature() throws Exception {
        Measurement createdMeasurement = ressource.createMeasurement(MEASUREMENT_1);
        String newId = createdMeasurement.getId();

        Measurement retrievedMeasurement = ressource.getTemperature(newId);
        Assert.assertEquals(createdMeasurement, retrievedMeasurement);
    }

    @Test
    public void testGetTemperatureFail() throws Exception {

        try {
            ressource.getTemperatures(new MyUriInfo(QUERY_MAP_4));
        }
        catch (MeasurementDataAccessException e) {
            return;
        }

        Assert.fail("should throw a exception");
    }

    @Test
    public void testGetTemperatures() throws Exception {
        Measurement createdMeasurement_1 = ressource.createMeasurement(MEASUREMENT_1);

        Measurement[] measurements = ressource.getTemperatures(new MyUriInfo(QUERY_MAP_3));

        Assert.assertEquals(1, measurements.length);
    }


    class MyUriInfo implements UriInfo {
        private MultivaluedMap<String, String> params;

        public MyUriInfo(MultivaluedMap<String, String> params) {
            this.params = params;
        }

        public String getPath() {
            return null;
        }

        public String getPath(boolean decode) {
            return null;
        }

        public List<PathSegment> getPathSegments() {
            return null;
        }

        public List<PathSegment> getPathSegments(boolean decode) {
            return null;
        }

        public URI getRequestUri() {
            return null;
        }

        public UriBuilder getRequestUriBuilder() {
            return null;
        }

        public URI getAbsolutePath() {
            return null;
        }

        public UriBuilder getAbsolutePathBuilder() {
            return null;
        }

        public URI getBaseUri() {
            return null;
        }

        public UriBuilder getBaseUriBuilder() {
            return null;
        }

        public MultivaluedMap<String, String> getPathParameters() {
            return null;
        }

        public MultivaluedMap<String, String> getPathParameters(boolean decode) {
            return null;
        }

        public MultivaluedMap<String, String> getQueryParameters() {
            return params;
        }

        public MultivaluedMap<String, String> getQueryParameters(boolean decode) {
            return null;
        }

        public List<String> getMatchedURIs() {
            return null;
        }

        public List<String> getMatchedURIs(boolean decode) {
            return null;
        }

        public List<Object> getMatchedResources() {
            return null;
        }

        public URI resolve(URI uri) {
            return null;
        }

        public URI relativize(URI uri) {
            return null;
        }
    }

}
