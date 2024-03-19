package reseau_api;

import com.google.gson.annotations.SerializedName;

import unique.Pet;

public class SimpleApiResponse {
    @SerializedName("message")
    private String message;

    private Pet pet;

    public SimpleApiResponse(String message, Pet pet) {
        this.message = message;
        this.pet = pet;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    // Getter et Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
