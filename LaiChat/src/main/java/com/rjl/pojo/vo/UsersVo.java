package com.rjl.pojo.vo;

public class UsersVo {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUername() {
        return username;
    }

    public void setUername(String uername) {
        this.username = uername;
    }

    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    public String getIconImageBig() {
        return iconImageBig;
    }

    public void setIconImageBig(String iconImageBig) {
        this.iconImageBig = iconImageBig;
    }

    public String getNikcname() {
        return nickname;
    }

    public void setNikcname(String nikcname) {
        this.nickname = nikcname;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    private String id;
    private String username;
    private String iconImage;
    private String iconImageBig;
    private String nickname;
    private String qrCode;
}
