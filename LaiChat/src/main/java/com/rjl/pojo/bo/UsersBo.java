package com.rjl.pojo.bo;

public class UsersBO {
    private String userId;
    private String IconData;
    private String nickname;
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getIconData() {
		return IconData;
	}
	public void setFaceData(String faceData) {
		this.IconData = faceData;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
}
