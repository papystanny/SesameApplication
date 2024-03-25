package unique;

import com.google.gson.annotations.SerializedName;

public class Pet {
    @SerializedName("name")
    String name;
    @SerializedName("nickname")
    String nickname;
    @SerializedName("type")
    String type;
    @SerializedName("img")
    String img;
    @SerializedName("collar_tag")
    String collar_tag;
    @SerializedName("is_outside")
    int is_outside_int;

    boolean is_outside_bool;

    public boolean getStatus() {
        return is_outside_bool;
    }

    public void setStatus(Boolean status) {
        this.is_outside_bool = status;
    }

    public Pet(String name, String nickname, String type, String img, String collar_tag, int is_outside_int) {
        this.name = name;
        this.nickname = nickname;
        this.type = type;
        this.img = img;
        this.is_outside_int = is_outside_int;

        this.is_outside_bool = is_outside_int == 1;
    }

    public Pet(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getCollar_tag() {
        return collar_tag;
    }

    public void setCollar_tag(String collar_tag) {
        this.collar_tag = collar_tag;
    }

}
