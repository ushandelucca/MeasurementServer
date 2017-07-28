package de.oo2.tank.server.measurement;

import de.oo2.tank.server.util.PersistenceException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static de.oo2.tank.server.measurement.MeasurementFixture.*;

/**
 * Integration tests for the persistence.
 */
public class MeasurementDaoIntTest {

    private MeasurementDao dao;

    @Before
    public void setUp() throws Exception {
        dao = new MeasurementDao("intTest", "localhost", 27017);
    }

    @After
    public void tearDown() throws Exception {
        dao.getMeasurements().drop();
        dao.closeMeasurements();
        dao = null;
    }

    @Test
    public void testInstance() throws Exception {
        Assert.assertNotNull(dao);
    }

    @Test
    public void testCreateMeasurement() throws Exception {
        Measurement result = dao.createMeasurement(getMeasurement1());
        String id_1 = result.getId();
        Assert.assertNotEquals("", id_1);

        result = dao.createMeasurement(getMeasurement2());
        String id_2 = result.getId();
        Assert.assertNotEquals("", id_2);
        Assert.assertNotEquals(id_1, id_2);

        result = dao.createMeasurement(getMeasurement3());
        String id_3 = result.getId();
        Assert.assertNotEquals("", id_3);
        Assert.assertNotEquals(id_2, id_3);
        Assert.assertNotEquals(id_1, id_3);
    }

    @Test
    public void testReadMeasurementById() throws Exception {
        String id1 = dao.createMeasurement(getMeasurement1()).getId();
        String id2 = dao.createMeasurement(getMeasurement2()).getId();
        String id3 = dao.createMeasurement(getMeasurement3()).getId();

        Measurement measurement = dao.readMeasurementWithId(id3);
        Assert.assertEquals(getMeasurement3().getTimestamp(), measurement.getTimestamp());

        measurement = dao.readMeasurementWithId(id1);
        Assert.assertEquals(getMeasurement1().getTimestamp(), measurement.getTimestamp());

        measurement = dao.readMeasurementWithId(id2);
        Assert.assertEquals(getMeasurement2().getTimestamp(), measurement.getTimestamp());
    }

    @Test
    public void testReadMeasurementsWithQuery1() throws Exception {
        dao.createMeasurement(getMeasurement1());
        dao.createMeasurement(getMeasurement2());
        dao.createMeasurement(getMeasurement3());

        Map<String, String[]> params = new HashMap<>();
        params.put("query", new String[]{"return"});
        params.put("max_result", new String[]{"3"});

        Measurement[] measurements = dao.readMeasurementsWithQuery(params);

        Assert.assertEquals(3, measurements.length);
    }

    @Test
    public void testReadMeasurementsWithQueryDate() throws Exception {
        dao.createMeasurement(getMeasurement1());
        dao.createMeasurement(getMeasurement2());
        dao.createMeasurement(getMeasurement3());

        Map<String, String[]> params = new HashMap<>();
        params.put("query", new String[]{"return"});
        params.put("begin", new String[]{"2014-01-20"});
        params.put("end", new String[]{"2015-01-20"});

        Measurement[] measurements = dao.readMeasurementsWithQuery(params);

        Assert.assertEquals(0, measurements.length);
    }

    @Test
    public void testReadMeasurementsWithQuerySensor() throws Exception {
        dao.createMeasurement(getMeasurement1());
        dao.createMeasurement(getMeasurement2());
        dao.createMeasurement(getMeasurement3());

        Map<String, String[]> params = new HashMap<>();
        params.put("query", new String[]{"return"});
        params.put("sensor", new String[]{"Precipitation"});

        Measurement[] measurements = dao.readMeasurementsWithQuery(params);

        Assert.assertEquals(3, measurements.length);
    }

    @Test
    public void testReadMeasurementsWithQueryDateAndSensor() throws Exception {
        dao.createMeasurement(getMeasurement1());
        dao.createMeasurement(getMeasurement2());
        dao.createMeasurement(getMeasurement3());

        Map<String, String[]> params = new HashMap<>();
        params.put("query", new String[]{"return"});
        params.put("sensor", new String[]{"Precipitation"});
        params.put("begin", new String[]{"2014-01-20"});
        params.put("end", new String[]{"2015-01-20"});

        Measurement[] measurements = dao.readMeasurementsWithQuery(params);

        Assert.assertEquals(0, measurements.length);
    }

    @Test
    public void testReadMeasurementsWithQueryFail() throws Exception {
        Map<String, String[]> params = new HashMap<>();

        try {
            dao.readMeasurementsWithQuery(params);
        } catch (PersistenceException e) {
            return;
        }

        Assert.fail("should throw a exception");
    }

    @Test
    public void testUpdateMeasurement() throws Exception {
        Measurement measurement = getMeasurement1();

        measurement = dao.createMeasurement(measurement);

        Measurement measurementInDb = dao.readMeasurementWithId(measurement.getId());
        Assert.assertEquals(measurement, measurementInDb);

        float newValue = 99.7654321f;

        measurement.setValue(newValue);
        measurement = dao.updateMeasurement(measurement);

        measurementInDb = dao.readMeasurementWithId(measurement.getId());
        Assert.assertEquals(measurement, measurementInDb);
    }

    @Test
    public void testUpdateMeasurementFail() throws Exception {
        Measurement measurement = getMeasurement1();
        measurement = dao.createMeasurement(measurement);
        dao.deleteMeasurement(measurement.getId());

        try {
            dao.updateMeasurement(measurement);
        } catch (PersistenceException e) {
            return;
        }

        Assert.fail("should throw a exception");
    }

    @Test
    public void testDeleteMeasurement() throws Exception {
        Measurement measurement = getMeasurement1();
        measurement = dao.createMeasurement(measurement);
        dao.deleteMeasurement(measurement.getId());
    }

    @Test
    public void testDeleteMeasurementFail() throws Exception {
        Measurement measurement = getMeasurement1();
        measurement = dao.createMeasurement(measurement);
        dao.deleteMeasurement(measurement.getId());

        try {
            dao.deleteMeasurement(measurement.getId());
        } catch (PersistenceException e) {
            return;
        }

        Assert.fail("should throw a exception");
    }
}
