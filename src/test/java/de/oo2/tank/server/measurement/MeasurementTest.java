package de.oo2.tank.server.measurement;

import org.junit.Assert;
import org.junit.Test;

import static de.oo2.tank.server.measurement.MeasurementFixture.getMeasurement1;

/**
 * Tests for the <code>Measurement</code> class.
 */
public class MeasurementTest {

    @Test
    public void testMeasurementEquals() throws Exception {
        Measurement measurement1 = getMeasurement1();
        Measurement measurement2 = new Measurement();

        Assert.assertFalse(measurement1.equals(measurement2));

        measurement2.setId(measurement1.getId());
        Assert.assertFalse(measurement1.equals(measurement2));

        measurement2.setTimestamp(measurement1.getTimestamp());
        Assert.assertFalse(measurement1.equals(measurement2));

        measurement2.setSensor(measurement1.getSensor());
        Assert.assertFalse(measurement1.equals(measurement2));

        measurement2.setValue(measurement1.getValue());
        Assert.assertFalse(measurement1.equals(measurement2));

        measurement2.setUnit(measurement1.getUnit());
        Assert.assertTrue(measurement1.equals(measurement2));

        Assert.assertFalse(measurement1.equals(null));
        Assert.assertFalse(measurement1.equals(""));
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