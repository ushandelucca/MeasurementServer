package de.oo2.tank.server;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.Arrays;
import java.util.Date;

/**
 * Fixture for the unit tests.
 */
public class MeasurementFixture {

    public static final Measurement TEMP_MEASUREMENT_1;
    public static final Measurement TEMP_MEASUREMENT_2;
    public static final Measurement TEMP_MEASUREMENT_3;

    static {
        TEMP_MEASUREMENT_1 = new Measurement();
        TEMP_MEASUREMENT_1.setTimestamp(new Date(System.currentTimeMillis()));
        TEMP_MEASUREMENT_1.setSensor("Temperature");
        TEMP_MEASUREMENT_1.setValue(1.2f);
        TEMP_MEASUREMENT_1.setUnit("°C");
        TEMP_MEASUREMENT_1.setValid(true);

        TEMP_MEASUREMENT_2 = new Measurement();
        TEMP_MEASUREMENT_2.setTimestamp(new Date(System.currentTimeMillis()));
        TEMP_MEASUREMENT_2.setSensor("Temperature");
        TEMP_MEASUREMENT_2.setValue(2.5f);
        TEMP_MEASUREMENT_2.setUnit("°C");
        TEMP_MEASUREMENT_2.setValid(true);

        TEMP_MEASUREMENT_3 = new Measurement();
        TEMP_MEASUREMENT_3.setTimestamp(new Date(System.currentTimeMillis()));
        TEMP_MEASUREMENT_3.setSensor("Temperature");
        TEMP_MEASUREMENT_3.setValue(3.8f);
        TEMP_MEASUREMENT_3.setUnit("°C");
        TEMP_MEASUREMENT_3.setValid(true);
    }

}
