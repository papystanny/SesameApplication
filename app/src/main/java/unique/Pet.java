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
    Boolean is_outside;

    public Boolean getStatus() {
        return is_outside;
    }

    public void setStatus(Boolean status) {
        this.is_outside = status;
    }

    public Pet(String name, String nickname, String type, String img, String collar_tag, Boolean is_outside) {
        this.name = name;
        this.nickname = nickname;
        this.type = type;
        this.img = img;
        this.collar_tag = collar_tag;
        this.is_outside = is_outside;
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
