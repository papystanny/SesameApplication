package unique;

public class Pet {

    String name;
    String nickname;
    String type;
    String img;
    String collar_tag;
    Boolean status;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Pet(String name, String nickname, String type, String img, String collar_tag, Boolean status) {
        this.name = name;
        this.nickname = nickname;
        this.type = type;
        this.img = img;
        this.collar_tag = collar_tag;
        this.status = status;
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
