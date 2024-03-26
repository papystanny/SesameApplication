package unique;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class PetActivity {
    // VARIABLES
    @SerializedName("in_or_out")
    private int inOrOut;
    private String time;
    private String date;
    private String pet;
    @SerializedName("collar_tag")
    private String collar_tag;
    @SerializedName("created_at")
    private String created_at;
    private String totalActivity;
    private List<TimePeriod> outsidePeriods;


    // CONSTRUCTOR
    public PetActivity(int inOrOut, String created_at, String collar_tag) {
           this.inOrOut = inOrOut;
            this.created_at = created_at;
            this.collar_tag = collar_tag;
            this.outsidePeriods = new ArrayList<>();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");

        Date dateTime = null;
        try {
            dateTime = sdf.parse(created_at);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        time = sdfTime.format(dateTime);
        date = sdfDate.format(dateTime);
        totalActivity += time;
    }

    // METHODS
    private String validateTime(String time) {
        // Check if time contains seconds
        if (time.matches("(\\d{2}:\\d{2}:\\d{2})")) {
            // Remove seconds
            String cut = time.substring(0, 5); // Take only hours and minutes
            String finalTime = cut.replace(":", "h"); // Replace ':' with 'h'
            return finalTime;
        }
        return time; // Return unchanged time if seconds are not present
    }

    // GETTERS AND SETTERS
    public int isInOrOut() {
        return inOrOut;
    }

    public void setInside(int outside) {
        inOrOut = inOrOut;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public String getCollar_tag() {
        return collar_tag;
    }

    public void setCollar_tag(String collar_tag) {
        this.collar_tag = collar_tag;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getTotalActivity() {
        return totalActivity;
    }

    public void setTotalActivity(String totalActivity) {
        this.totalActivity = totalActivity;
    }

    @NonNull
    @Override
    public String toString() {
        return "PetActivity{" +
                "inOrOut=" + inOrOut +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                ", pet='" + pet + '\'' +
                ", collar_tag='" + collar_tag + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }

    // Inner class to represent a time period
    private static class TimePeriod {
        private String startTime;
        private String endTime;

        public TimePeriod(String startTime, String endTime) {
            this.startTime = startTime;
            this.endTime = endTime;
        }

        public String getStartTime() {
            return startTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }
    }
}
