package de.oo2.tank.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

/**
 * Fixture for the unit tests.
 */
public class MeasurementFixture {
    public static final Measurement MEASUREMENT_1;
    public static final Measurement MEASUREMENT_2;
    public static final Measurement MEASUREMENT_3;
    private static LocalDate today = LocalDate.now();

    static {
        int pastDays = 0;

        MEASUREMENT_1 = new Measurement();
        MEASUREMENT_1.setTimestamp(convertToDate(today.minusDays(pastDays++)));
        MEASUREMENT_1.setSensor("Temperature");
        MEASUREMENT_1.setValue(1.2f);
        MEASUREMENT_1.setUnit("°C");
        MEASUREMENT_1.setValid(true);

        MEASUREMENT_2 = new Measurement();
        MEASUREMENT_1.setTimestamp(convertToDate(today.minusDays(pastDays++)));
        MEASUREMENT_2.setSensor("Temperature");
        MEASUREMENT_2.setValue(2.5f);
        MEASUREMENT_2.setUnit("°C");
        MEASUREMENT_2.setValid(true);

        MEASUREMENT_3 = new Measurement();
        MEASUREMENT_1.setTimestamp(convertToDate(today.minusDays(pastDays++)));
        MEASUREMENT_3.setSensor("Temperature");
        MEASUREMENT_3.setValue(3.8f);
        MEASUREMENT_3.setUnit("°C");
        MEASUREMENT_3.setValid(true);
    }

    private static Date convertToDate(LocalDate date) {
        return Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    public static Measurement setRandomId(Measurement measurement) {
        measurement.setId(UUID.randomUUID().toString());
        return measurement;
    }

    public static Measurement getMeasurement1() {
        Measurement m = new Measurement();
        m.setTimestamp(convertToDate(today.minusDays(1)));
        m.setSensor("Temperature");
        m.setValue(1.2f);
        m.setUnit("°C");
        m.setValid(true);

        return m;
    }

    public static Measurement getMeasurement2() {
        Measurement m = new Measurement();
        m.setTimestamp(convertToDate(today.minusDays(2)));
        m.setSensor("Temperature");
        m.setValue(2.5f);
        m.setUnit("°C");
        m.setValid(true);

        return m;
    }

}
