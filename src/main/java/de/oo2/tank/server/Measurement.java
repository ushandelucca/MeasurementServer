package de.oo2.tank.server;

import org.jongo.marshall.jackson.oid.MongoObjectId;

import java.util.Date;

/**
 * Value object for the measurements.
 */
public class Measurement {

    @MongoObjectId
    private String _id;

    private Date timestamp;

    private String sensor;

    private Float value;

    private String unit;

    private Boolean valid;

    /**
     * Returns the unique id (primary key) of the measurement.
     * @return the id if the measuremst is stored in the db. If the id == <code>null</code> the measurememt is not
     * stored in the db.
     */
    public String getId() {
        return _id;
    }

    /**
     * Set the id of the measurement
     * @param id the timestamp
     */
    void setId(String id) {
        this._id = id;
    }

    /**
     * Returns the timestamp of the measurement
     * @return the timestamp
     */
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
     * Returns the sensor name of the measurement. E. g. Temperature, Waterlevel, stc.
     * @return the name of the sensor
     */
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

    /**
     * Returns <code>true</code> if the measurement is valid/consistent, otherwise <code>false</code>.
     * @return <code>true</code> or <code>false</code>
     */
    public Boolean getValid() {
        return valid;
    }

    /**
     * Set <code>true</code> if the measurement is valid/consistent, otherwise <code>false</code>.
     * @param valid <code>true</code> or <code>false</code>
     */
    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Measurement that = (Measurement) o;

        return !(_id != null ? !_id.equals(that._id) : that._id != null) && !(sensor != null ? !sensor.equals(that.sensor) : that.sensor != null) && !(timestamp != null ? !timestamp.equals(that.timestamp) : that.timestamp != null) && !(unit != null ? !unit.equals(that.unit) : that.unit != null) && !(valid != null ? !valid.equals(that.valid) : that.valid != null) && !(value != null ? !value.equals(that.value) : that.value != null);

    }

    @Override
    public int hashCode() {
        int result = _id != null ? _id.hashCode() : 0;
        result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
        result = 31 * result + (sensor != null ? sensor.hashCode() : 0);
        result = 31 * result + (value != null ? value.hashCode() : 0);
        result = 31 * result + (unit != null ? unit.hashCode() : 0);
        result = 31 * result + (valid != null ? valid.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "_id='" + _id + '\'' +
                ", timestamp=" + timestamp +
                ", sensor='" + sensor + '\'' +
                ", value=" + value +
                ", unit='" + unit + '\'' +
                ", valid=" + valid +
                '}';
    }
}
