package unique;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

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


    // CONSTRUCTOR
    public PetActivity(int inOrOut, String created_at, String collar_tag) {
        this.inOrOut = inOrOut;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date dateTime = sdf.parse(created_at);
            SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");

            this.date = sdfDate.format(dateTime);
            this.time = sdfTime.format(dateTime);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        this.collar_tag = collar_tag;
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
}
