package de.oo2.tank.server.model;

import org.junit.Assert;
import org.junit.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Tests for the measurement validation.
 * TODO: complete the test
 */
public class MeasurementValidationTest {

    @Test
    public void testMemberWithNoValues() {
        Measurement measurement = new Measurement();

        // http://hibernate.org/validator/documentation/getting-started/


        // validate the input
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Measurement>> violations = validator.validate(measurement);
        Assert.assertEquals(violations.size(), 5);
    }

}
