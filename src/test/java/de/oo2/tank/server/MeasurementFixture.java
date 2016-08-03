package de.oo2.tank.server;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.UUID;

/**
 * Fixture for the unit tests.
 */
public class MeasurementFixture {
    private static LocalDate today = LocalDate.now();

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

    public static Measurement getMeasurement3() {
        Measurement m = new Measurement();
        m.setTimestamp(convertToDate(today.minusDays(3)));
        m.setSensor("Temperature");
        m.setValue(3.5f);
        m.setUnit("°C");
        m.setValid(true);

        return m;
    }
}
