package de.oo2.tank.server.measurement;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static de.oo2.tank.server.measurement.MeasurementFixture.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for <code>MeasurementController</code>
 */
public class MeasurementControllerTest {
    private MeasurementController service = null;
    private MeasurementDao daoMock = null;

    @Before
    public void setUp() throws Exception {
        daoMock = mock(MeasurementDao.class);
        service = new MeasurementController(daoMock);
    }

    @After
    public void tearDown() throws Exception {
        service = null;
    }

    @Test
    public void testSaveMeasurement() throws Exception {
        // setup the mock
        Measurement measurementToDb = getMeasurement1();
        Measurement measurementFromDb = setRandomId(measurementToDb);
        when(daoMock.createMeasurement(measurementToDb)).thenReturn(measurementFromDb);

        // test
        Measurement measurement = service.saveMeasurement(measurementToDb);

        Assert.assertNotNull(measurement.getId());
    }

    @Test
    public void testReadMeasurement() throws Exception {
        // setup the mock
        Measurement measurementToDb = getMeasurement2();
        Measurement measurementFromDb = setRandomId(measurementToDb);
        when(daoMock.readMeasurementWithId(measurementFromDb.getId())).thenReturn(measurementFromDb);

        // test
        Measurement measurement = service.readMeasurement(measurementFromDb.getId());

        Assert.assertEquals(measurementFromDb.getId(), measurement.getId());
    }

    @Test
    public void testGetMeasurements() throws Exception {
        // setup the mock
        Measurement[] measurementsFromDb = new Measurement[]{setRandomId(getMeasurement3()), setRandomId(getMeasurement2())};

        Map<String, String[]> params = new HashMap<>();
        params.put("query", new String[]{"return"});
        params.put("max_result", new String[]{"30"});
        when(daoMock.readMeasurementsWithQuery(params)).thenReturn(measurementsFromDb);

        // test
        Measurement[] measurements = service.selectMeasurements(params);

        Assert.assertNotNull(measurements);
        Assert.assertTrue(measurements.length > 0);
    }

    @Test
    public void testMeasurementValidation() throws Exception {
        Measurement measurement = new Measurement();

        try {
            service.saveMeasurement(measurement);
        } catch (MeasurementException e) {
            Assert.assertNotNull(e.getMessage());
            return;
        }

        Assert.fail("MeasurementException expected");
    }

    @Test
    public void testUpdateMeasurement() throws Exception {
        // setup the mock
        Measurement measurement = getMeasurement1();
        measurement = setRandomId(measurement);
        when(daoMock.updateMeasurement(measurement)).thenReturn(measurement);

        // test
        Measurement updatedMeasurement = service.updateMeasurement(measurement);

        Assert.assertEquals(measurement, updatedMeasurement);
    }

    @Test
    public void testUpdateMeasurementFail() throws Exception {
        // setup the mock
        Measurement measurement = getMeasurement1();
        when(daoMock.updateMeasurement(measurement)).thenReturn(measurement);

        try {
            service.updateMeasurement(measurement);
        } catch (MeasurementException e) {
            Assert.assertNotNull(e.getMessage());
            return;
        }

        Assert.fail("MeasurementException expected");
    }

    @Test
    public void testDeleteMeasurement() throws Exception {
        // setup the mock
        Measurement measurement = getMeasurement1();
        measurement = setRandomId(measurement);
        when(daoMock.updateMeasurement(measurement)).thenReturn(measurement);

        // test
        service.deleteMeasurement(measurement.getId());
    }

    @Test
    public void testDeleteMeasurementFail() throws Exception {
        // setup the mock
        Measurement measurement = getMeasurement1();
        measurement = setRandomId(measurement);
        Mockito.doThrow(new MeasurementDaoException("")).when(daoMock).deleteMeasurement(measurement.getId());

        // test
        try {
            service.deleteMeasurement(measurement.getId());
        } catch (MeasurementDaoException e) {
            Assert.assertNotNull(e.getMessage());
            return;
        }

        Assert.fail("MeasurementDaoException expected");
    }
}
