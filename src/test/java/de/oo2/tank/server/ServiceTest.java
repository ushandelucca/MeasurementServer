package de.oo2.tank.server;

import de.oo2.tank.server.dao.MeasurementDataAccessException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

import static de.oo2.tank.server.QueryFixture.QUERY_MAP_3;
import static de.oo2.tank.server.MeasurementFixture.*;


public class ServiceTest {
    private Service service;

    @Before
    public void setUp() throws Exception {
        service = new Service();
    }

    @After
    public void tearDown() throws Exception {
        service = null;
    }

    @Test
    public void testCreateMeasurement() throws Exception {
        Measurement measurement = service.createMeasurement(TEMP_MEASUREMENT_1);
        Assert.assertNotNull(measurement.getId());
    }

    @Test
    public void testGetTemperature() throws Exception {
        Measurement createdMeasurement = service.createMeasurement(TEMP_MEASUREMENT_1);
        String newId = createdMeasurement.getId();

        Measurement retrievedMeasurement = service.getTemperature(newId);
        Assert.assertEquals(createdMeasurement, retrievedMeasurement);
    }

    @Test
    public void testGetTemperatureFail() throws Exception {

        try {
            service.getTemperatures(new MyUriInfo());
        }
        catch (MeasurementDataAccessException e) {
            return;
        }

        Assert.fail("should throw a exception");
    }

    @Test
    public void testGetTemperatures() throws Exception {
        Measurement createdMeasurement_1 = service.createMeasurement(TEMP_MEASUREMENT_1);

        Measurement[] measurements = service.getTemperatures(new MyUriInfo());

        Assert.assertEquals(4, measurements.length);
    }


    class MyUriInfo implements UriInfo {
        @Override
        public String getPath() {
            return null;
        }

        @Override
        public String getPath(boolean decode) {
            return null;
        }

        @Override
        public List<PathSegment> getPathSegments() {
            return null;
        }

        @Override
        public List<PathSegment> getPathSegments(boolean decode) {
            return null;
        }

        @Override
        public URI getRequestUri() {
            return null;
        }

        @Override
        public UriBuilder getRequestUriBuilder() {
            return null;
        }

        @Override
        public URI getAbsolutePath() {
            return null;
        }

        @Override
        public UriBuilder getAbsolutePathBuilder() {
            return null;
        }

        @Override
        public URI getBaseUri() {
            return null;
        }

        @Override
        public UriBuilder getBaseUriBuilder() {
            return null;
        }

        @Override
        public MultivaluedMap<String, String> getPathParameters() {
            return null;
        }

        @Override
        public MultivaluedMap<String, String> getPathParameters(boolean decode) {
            return null;
        }

        @Override
        public MultivaluedMap<String, String> getQueryParameters() {
            return QUERY_MAP_3;
        }

        @Override
        public MultivaluedMap<String, String> getQueryParameters(boolean decode) {
            return null;
        }

        @Override
        public List<String> getMatchedURIs() {
            return null;
        }

        @Override
        public List<String> getMatchedURIs(boolean decode) {
            return null;
        }

        @Override
        public List<Object> getMatchedResources() {
            return null;
        }

        @Override
        public URI resolve(URI uri) {
            return null;
        }

        @Override
        public URI relativize(URI uri) {
            return null;
        }
    }

}
