package de.oo2.tank.server.dao;

import de.oo2.tank.model.Measurement;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static de.oo2.tank.model.MeasurementFixture.*;

public class MeasurementDaoTest {

    private MeasurementDao dao;

    @Before
    public void setUp() throws Exception {
        dao = new MeasurementDao("test");
        dao.getMeasurements().drop();
    }

    @After
    public void tearDown() throws Exception {
        dao = null;
    }

    @Test
    public void testGetInstance() throws Exception {
        Assert.assertNotNull(dao);
    }

    @Test
    public void testCreateMeasurement() throws Exception {
        Measurement result = dao.createMeasurement(MEASUREMENT_1);
        String id_1 = result.getId();
        Assert.assertNotEquals("", id_1);

        result = dao.createMeasurement(MEASUREMENT_2);
        String id_2 = result.getId();
        Assert.assertNotEquals("", id_2);
        Assert.assertNotEquals(id_1, id_2);

        result = dao.createMeasurement(MEASUREMENT_3);
        String id_3 = result.getId();
        Assert.assertNotEquals("", id_3);
        Assert.assertNotEquals(id_2, id_3);
        Assert.assertNotEquals(id_1, id_3);
    }

    @Test
    public void testReadMeasurementById() throws Exception {
        String id1 = dao.createMeasurement(MEASUREMENT_1).getId();
        String id2 = dao.createMeasurement(MEASUREMENT_2).getId();
        String id3 = dao.createMeasurement(MEASUREMENT_3).getId();

        Measurement measurement = dao.readMeasurementById(id3);
        Assert.assertEquals(MEASUREMENT_3, measurement);

        measurement = dao.readMeasurementById(id1);
        Assert.assertEquals(MEASUREMENT_1, measurement);

        measurement = dao.readMeasurementById(id2);
        Assert.assertEquals(MEASUREMENT_2, measurement);
    }

    @Test
    public void testReadMeasurementsWithQuery() throws Exception {
        dao.createMeasurement(MEASUREMENT_1);
        dao.createMeasurement(MEASUREMENT_2);
        dao.createMeasurement(MEASUREMENT_3);

        Measurement[] measurements = dao.readMeasurementsWithQuery("{sensor: 'Temperature'}", "{}", 0);

        Assert.assertEquals(3, measurements.length);
    }

    @Test
    public void testReadMeasurementsWithQueryFail() throws Exception {

        try {
            dao.readMeasurementsWithQuery("toFail", "", 0);
        }
        catch (MeasurementDataAccessException e) {
            return;
        }

        Assert.fail("should throw a exception");
    }

    @Test
    public void testUpdateMeasurement() throws Exception {
        dao.updateMeasurement(1);
        // TODO implementation
    }

    @Test
    public void testDeleteMeasurement() throws Exception {
        dao.deleteMeasurement(1);
        // TODO implementation
    }
}
