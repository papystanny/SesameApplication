package unique;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("firstname")
    String firstname ;
    @SerializedName("lastname")
    String lastname ;
    @SerializedName("phone")
    String phone;
    @SerializedName("email")
    String email ;
    @SerializedName("password")
    String password;
    @SerializedName("id")
    int id;


    public User( int id, String firstname, String lastname, String phone, String email, String password) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



}
