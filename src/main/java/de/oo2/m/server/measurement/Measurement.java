package de.oo2.m.server.measurement;

import org.hibernate.validator.constraints.NotBlank;
import org.jongo.marshall.jackson.oid.MongoId;
import org.jongo.marshall.jackson.oid.MongoObjectId;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

/**
 * Value object for the measurements.
 */
public class Measurement {

    @MongoId
    @MongoObjectId
    private String id;

    private Date timestamp;

    private String sensor;

    private Float value;

    private String unit;

    /**
     * Returns the unique id (primary key) of the measurement.
     * @return the id if the measurement is stored in the db. If the id == <code>null</code> the measurement is not
     * stored in the db.
     */
    public String getId() {
        return id;
    }

    /**
     * Set the id of the measurement
     * @param id the timestamp
     */
    void setId(String id) {
        this.id = id;
    }

    /**
     * Set the id to <code>null</code>
     */
    void resetId() {
        this.id = null;
    }

    /**
     * Returns the timestamp of the measurement
     * @return the timestamp
     */
    @NotNull(message = "Timestamp is mandatory")
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Set the timestamp of the measurement
     * @param timestamp the timestamp
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Returns the sensor name of the measurement. E. g. Rain, Waterlevel, stc.
     * @return the name of the sensor
     */
    // @NotNull(message = "Sensor name is mandatory.")
    @NotBlank(message = "Sensor name is mandatory")
    @Pattern(regexp = "[a-z-A-Z]*", message = "Sensor name contains invalid characters")
    public String getSensor() {
        return sensor;
    }

    /**
     * Set the sensor name of the measurement
     * @param sensor the sensor name
     */
    public void setSensor(String sensor) {
        this.sensor = sensor;
    }

    /**
     * Returns the measured value of this measurement.
     * @return the value of the measurement
     */
    @NotNull(message = "Value is mandatory")
    @Min(value = -1000, message = "Value must be greater than or equal to -1000")
    @Max(value = 1000, message = "Value must be less than or equal to 1000")
    public Float getValue() {
        return value;
    }

    /**
     * Set the measured value of the measurement
     * @param value the value
     */
    public void setValue(Float value) {
        this.value = value;
    }

    /**
     * Returns the unit of the measured value.
     * @return the unit
     */
    // @NotNull(message = "Unit is mandatory.")
    @NotBlank(message = "Unit is mandatory")
    @Pattern(regexp = "[a-z-A-Z]*", message = "Unit contains invalid characters")
    public String getUnit() {
        return unit;
    }

    /**
     * Set the unit of the measured value.
     * @param unit the unit
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Measurement that = (Measurement) o;

        return !(id != null ? !id.equals(that.id) : that.id != null) && !(sensor != null ? !sensor.equals(that.sensor) : that.sensor != null) && !(timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) && !(unit != null ? !unit.equals(that.unit) : that.unit != null) && !(value != null ? !value.equals(that.value) : that.value != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (sensor != null ? sensor.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "id='" + id + '\'' +
                ", timestamp=" + timestamp +
                ", sensor='" + sensor + '\'' +
                ", value=" + value +
                ", unit='" + unit + '\'' +
                '}';
    }
}
