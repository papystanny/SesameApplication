package reseau_api;

import com.google.gson.annotations.SerializedName;

import unique.User;

public class ApiErrorResponse {
    @SerializedName("message")
    private String message;

    @SerializedName("user")
    private User user;

    @SerializedName("token")
    private String token;


    // Getter et Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}