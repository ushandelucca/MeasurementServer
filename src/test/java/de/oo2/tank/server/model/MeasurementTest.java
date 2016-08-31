package de.oo2.tank.server.model;

import org.junit.Assert;
import org.junit.Test;

import static de.oo2.tank.server.model.MeasurementFixture.getMeasurement1;

/**
 * Tests for the <code>Measurement</code> class.
 */
public class MeasurementTest {

    @Test
    public void testMeasurementEquals() throws Exception {
        Measurement measurement1 = getMeasurement1();

        Measurement measurement2 = new Measurement();

        measurement2.setId(measurement1.getId());
        measurement2.setTimestamp(measurement1.getTimestamp());
        measurement2.setSensor(measurement1.getSensor());
        measurement2.setValue(measurement1.getValue());
        measurement2.setUnit(measurement1.getUnit());
        measurement2.setValid(measurement1.getValid());

        Assert.assertTrue(measurement1.equals(measurement2));
    }

    @Test
    public void testMeasurementHashCode() throws Exception {
        Measurement measurement = getMeasurement1();

        Assert.assertTrue(measurement.hashCode() != 0);
    }

    @Test
    public void testMeasurementToString() throws Exception {
        Measurement measurement = getMeasurement1();

        Assert.assertNotNull(measurement.toString());
    }

}