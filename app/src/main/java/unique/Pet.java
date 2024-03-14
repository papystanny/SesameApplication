package unique;

import com.google.gson.annotations.SerializedName;

public class Pet {

    @SerializedName("id")
    int id;
    @SerializedName("id_user")
    int id_user;
    @SerializedName("name")
    String name;
    @SerializedName("nickname")
    String nickname;
    @SerializedName("type")
    String type;
    @SerializedName("birth_date")
    String birthdate;
    @SerializedName("img")
    String img;
    @SerializedName("collar_tag")
    String collar_tag;
    @SerializedName("is_outside")
    Boolean isOutside;

    public Boolean getIsOutside() {
        return isOutside;
    }

    public void setIsOutside(Boolean isOutside) {
        this.isOutside = isOutside;
    }

    public Pet(int id, int id_user, String name, String nickname, String type, String birthdate, String img, String collar_tag, Boolean isOutside) {
        this.id = id;
        this.id_user = id_user;
        this.name = name;
        this.nickname = nickname;
        this.type = type;
        this.birthdate = birthdate;
        this.img = img;
        this.collar_tag = collar_tag;
        this.isOutside = isOutside;
    }

    public int getId() {
        return id;
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
