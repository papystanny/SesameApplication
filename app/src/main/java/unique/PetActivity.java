package unique;

public class PetActivity {
    // VARIABLES
    private boolean inOrOut;
    private String time, date, pet;


    // CONSTRUCTOR
    public PetActivity(boolean inOrOut, String time, String date, String pet) {
        this.inOrOut = inOrOut;
        this.time = time;
        this.date = date;
        this.pet = pet;
    }


    // GETTERS AND SETTERS
    public boolean isInOrOut() {
        return inOrOut;
    }

    public void setInside(boolean outside) {
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
}
