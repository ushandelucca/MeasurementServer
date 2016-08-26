package de.oo2.tank.server;

import de.oo2.tank.server.model.Measurement;
import de.oo2.tank.server.model.ModelException;
import de.oo2.tank.server.persistence.MongoDao;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
        Measurement measurementToRaw = getMeasurement1();
        Measurement measurementFromDb = setRandomId(measurementToRaw);
        when(daoMock.createMeasurement(measurementToRaw)).thenReturn(measurementFromDb);

        // test
        Measurement measurement = service.saveMeasurement(measurementToRaw);

        Assert.assertNotNull(measurement.getId());
    }

    @Test
    public void testReadMeasurement() throws Exception {
        // setup the mock
        Measurement measurementToRaw = getMeasurement2();
        Measurement measurementFromDb = setRandomId(measurementToRaw);
        when(daoMock.readMeasurementById(measurementFromDb.getId())).thenReturn(measurementFromDb);

        // test
        Measurement measurement = service.readMeasurement(measurementFromDb.getId());

        Assert.assertEquals(measurementFromDb.getId(), measurement.getId());
    }

    @Test
    public void testGetMeasurements() throws Exception {
        // setup the mock
        Measurement[] measurementsFromDb = new Measurement[]{setRandomId(getMeasurement3()), setRandomId(getMeasurement2())};
        when(daoMock.readMeasurementsWithQuery("query=return&&max_result=30")).thenReturn(measurementsFromDb);

        // test
        Measurement[] measurements = service.selectMeasurements("query=return&&max_result=30");

        Assert.assertNotNull(measurements);
        Assert.assertTrue(measurements.length > 0);
    }

    @Test
    public void testMeasurementValidation() throws Exception {
        Measurement measurement = new Measurement();

        try {
            measurement = service.saveMeasurement(measurement);
        } catch (ModelException e) {
            Assert.assertNotNull(e.getMessage());
            return;
        }

        Assert.fail("ModelException expected");
    }


}
