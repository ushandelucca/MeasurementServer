package de.oo2.tank.server;

import de.oo2.tank.server.dao.MongoDao;
import de.oo2.tank.server.model.Measurement;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static de.oo2.tank.server.model.MeasurementFixture.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Tests for <code>TemperatureService</code>
 */
public class TemperatureServiceTest {
    private TemperatureService service = null;
    private MongoDao daoMock = null;

    @Before
    public void setUp() throws Exception {
        daoMock = mock(MongoDao.class);
        service = new TemperatureService(daoMock);
    }

    @After
    public void tearDown() throws Exception {
        service = null;
    }

    @Test
    public void testSaveMeasurement() throws Exception {
        // setup the mock
        Measurement measurementToRaw = getMeasurement1();
        Measurement measurementFromDb = setRandomId(measurementToRaw);
        when(daoMock.createMeasurement(measurementToRaw)).thenReturn(measurementFromDb);

        // test
        Measurement measurement = service.saveTemperatue(measurementToRaw);

        Assert.assertNotNull(measurement.getId());
    }

    @Test
    public void testReadTemperature() throws Exception {
        // setup the mock
        Measurement measurementToRaw = getMeasurement2();
        Measurement measurementFromDb = setRandomId(measurementToRaw);
        when(daoMock.readMeasurementById(measurementFromDb.getId())).thenReturn(measurementFromDb);

        // test
        Measurement measurement = service.readTemperature(measurementFromDb.getId());

        Assert.assertEquals(measurementFromDb.getId(), measurement.getId());
    }

    @Test
    public void testGetTemperatures() throws Exception {
        // setup the mock
        Measurement[] measurementsFromDb = new Measurement[]{setRandomId(getMeasurement3()), setRandomId(getMeasurement2())};
        when(daoMock.readMeasurementsWithQuery("query=return&&max_result=30")).thenReturn(measurementsFromDb);

        // test
        Measurement[] measurements = service.selectTemperatures("query=return&&max_result=30");

        Assert.assertNotNull(measurements);
        Assert.assertTrue(measurements.length > 0);
    }
}
