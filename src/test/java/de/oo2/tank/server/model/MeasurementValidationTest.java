package de.oo2.tank.server.model;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static de.oo2.tank.server.model.MeasurementFixture.*;

/**
 * Tests for the measurement validation.
 * See: http://hibernate.org/validator/documentation/getting-started/
 */
public class MeasurementValidationTest {
    private static Validator validator;

    @BeforeClass
    public static void beforeClass() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testPlainMeasurement() {
        Measurement measurement = new Measurement();

        Set<ConstraintViolation<Measurement>> violations = validator.validate(measurement);
        Assert.assertEquals(4, violations.size());
    }

    @Test
    public void testMeasurement1() throws Exception {
        Measurement measurement = getMeasurement1();

        Set<ConstraintViolation<Measurement>> violations = validator.validate(measurement);
        Assert.assertEquals(0, violations.size());
    }

    @Test
    public void testMeasurement2() throws Exception {
        Measurement measurement = getMeasurement2();

        Set<ConstraintViolation<Measurement>> violations = validator.validate(measurement);
        Assert.assertEquals(0, violations.size());
    }

    @Test
    public void testMeasurement3() throws Exception {
        Measurement measurement = getMeasurement3();

        Set<ConstraintViolation<Measurement>> violations = validator.validate(measurement);
        Assert.assertEquals(0, violations.size());
    }

    @Test
    public void testTimestamp() throws Exception {
        Measurement measurement = getMeasurement1();
        measurement.setTimestamp(null);

        Set<ConstraintViolation<Measurement>> violations = validator.validate(measurement);
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void testSensor() throws Exception {
        Measurement measurement = getMeasurement1();
        measurement.setSensor("");

        Set<ConstraintViolation<Measurement>> violations = validator.validate(measurement);
        Assert.assertEquals(1, violations.size());

        measurement = getMeasurement2();
        measurement.setSensor("°Celsius sensor");

        violations = validator.validate(measurement);
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void testUnit() throws Exception {
        Measurement measurement = getMeasurement1();
        measurement.setUnit("");

        Set<ConstraintViolation<Measurement>> violations = validator.validate(measurement);
        Assert.assertEquals(1, violations.size());

        measurement = getMeasurement2();
        measurement.setUnit("°C");

        violations = validator.validate(measurement);
        Assert.assertEquals(1, violations.size());
    }

    @Test
    public void testValue() throws Exception {
        Measurement measurement = getMeasurement1();
        measurement.setValue(null);

        Set<ConstraintViolation<Measurement>> violations = validator.validate(measurement);
        Assert.assertEquals(1, violations.size());

        measurement = getMeasurement2();
        measurement.setValue(-1001.0f);

        violations = validator.validate(measurement);
        Assert.assertEquals(1, violations.size());

        measurement = getMeasurement3();
        measurement.setValue(1001.0f);

        violations = validator.validate(measurement);
        Assert.assertEquals(1, violations.size());
    }
}