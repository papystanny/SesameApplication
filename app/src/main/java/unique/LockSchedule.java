package unique;

import com.google.android.material.timepicker.TimeFormat;
import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class LockSchedule {

    // VARIABLES
    @SerializedName("id")
    private int id;
    @SerializedName("day_of_week")
    private String dayOfWeek;
    @SerializedName("opening_time")
    private String openTime;
    @SerializedName("closing_time")
    private String closeTime;
    @SerializedName("reccuring")
    private int recurring;

    // CONSTRUCTOR
    public LockSchedule(int id, String dayOfWeek, String openTime, String closeTime, int recurring) {
        this.id = id;
        this.dayOfWeek = dayOfWeek;
        this.openTime = validateTime(openTime);
        this.closeTime = validateTime(closeTime);
        this.recurring = recurring;
    }

    private String validateTime(String time) {
        // Check if time contains seconds
        if (time.matches("(\\d{2}:\\d{2}:\\d{2})")) {
            // Remove seconds
            return time.substring(0, 5); // Take only hours and minutes
        }
        return time; // Return unchanged time if seconds are not present
    }

    // GETTERS AND SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(String dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = validateTime(openTime);
    }

    public String getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(String closeTime) {
        this.closeTime = validateTime(closeTime);
    }

    public int getRecurring() {
        return recurring;
    }

    public void setRecurring(int recurring) {
        this.recurring = recurring;
    }
}
