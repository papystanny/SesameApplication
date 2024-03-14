package unique;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

public class Pet {

    @SerializedName("id")
    int id;
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
    int isOutside;

    public int getIsOutside() {
        return isOutside;
    }

    public void setIsOutside(int isOutside) {
        this.isOutside = isOutside;
    }

    public Pet(int id, String name, String nickname, String type, String img, String collar_tag, int isOutside) {
        this.id = id;
        this.name = name;
        this.nickname = nickname;
        this.type = type;
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

    @NonNull
    @Override
    public String toString() {
        return "Pet{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", nickname='" + nickname + '\'' +
                ", type='" + type + '\'' +
                ", img='" + img + '\'' +
                ", collar_tag='" + collar_tag + '\'' +
                ", isOutside=" + isOutside +
                '}';
    }
}
