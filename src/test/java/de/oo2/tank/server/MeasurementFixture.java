package de.oo2.tank.server;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 * Fixture for the unit tests.
 */
public class MeasurementFixture {

    public static final Measurement TEMP_MEASUREMENT_1;
    public static final Measurement TEMP_MEASUREMENT_2;
    public static final Measurement TEMP_MEASUREMENT_3;

    static {
        LocalDate today = LocalDate.now();
        int pastDays = 0;

        TEMP_MEASUREMENT_1 = new Measurement();
        TEMP_MEASUREMENT_1.setTimestamp(convertToDate(today.minusDays(pastDays++)));
        TEMP_MEASUREMENT_1.setSensor("Temperature");
        TEMP_MEASUREMENT_1.setValue(1.2f);
        TEMP_MEASUREMENT_1.setUnit("°C");
        TEMP_MEASUREMENT_1.setValid(true);

        TEMP_MEASUREMENT_2 = new Measurement();
        TEMP_MEASUREMENT_1.setTimestamp(convertToDate(today.minusDays(pastDays++)));
        TEMP_MEASUREMENT_2.setSensor("Temperature");
        TEMP_MEASUREMENT_2.setValue(2.5f);
        TEMP_MEASUREMENT_2.setUnit("°C");
        TEMP_MEASUREMENT_2.setValid(true);

        TEMP_MEASUREMENT_3 = new Measurement();
        TEMP_MEASUREMENT_1.setTimestamp(convertToDate(today.minusDays(pastDays++)));
        TEMP_MEASUREMENT_3.setSensor("Temperature");
        TEMP_MEASUREMENT_3.setValue(3.8f);
        TEMP_MEASUREMENT_3.setUnit("°C");
        TEMP_MEASUREMENT_3.setValid(true);
    }

    private static Date convertToDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

}
