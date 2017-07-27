package de.oo2.tank.server.service;

import de.oo2.tank.server.measurement.Measurement;
import de.oo2.tank.server.measurement.MeasurementService;
import de.oo2.tank.server.util.ModelException;
import de.oo2.tank.server.measurement.MongoDao;
import de.oo2.tank.server.util.PersistenceException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static de.oo2.tank.server.model.MeasurementFixture.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for <code>MeasurementService</code>
 */
public class MeasurementServiceTest {
    private MeasurementService service = null;
    private MongoDao daoMock = null;

    @Before
    public void setUp() throws Exception {
        daoMock = mock(MongoDao.class);
        service = new MeasurementService(daoMock);
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
        } catch (ModelException e) {
            Assert.assertNotNull(e.getMessage());
            return;
        }

        Assert.fail("ModelException expected");
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
        } catch (ModelException e) {
            Assert.assertNotNull(e.getMessage());
            return;
        }

        Assert.fail("ModelException expected");
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
        Mockito.doThrow(new PersistenceException("")).when(daoMock).deleteMeasurement(measurement.getId());

        // test
        try {
            service.deleteMeasurement(measurement.getId());
        } catch (PersistenceException e) {
            Assert.assertNotNull(e.getMessage());
            return;
        }

        Assert.fail("PersistenceException expected");
    }
}
