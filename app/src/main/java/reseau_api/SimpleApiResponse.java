package reseau_api;

import com.google.gson.annotations.SerializedName;

public class SimpleApiResponse {
    @SerializedName("message")
    private String message;

    // Getter et Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
