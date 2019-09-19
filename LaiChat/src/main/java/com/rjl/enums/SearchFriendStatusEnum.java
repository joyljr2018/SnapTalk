package com.rjl.enums;

public enum SearchFriendStatusEnum {


    SUCCESS(0, "OK"),
    USER_NOT_EXIST(1, "User not found..."),
    NOT_YOURSELF(2, "you can't add yourself..."),
    ALREADY_FRIENDS(3, "user is already your friend...");

    public final Integer status;
    public final String msg;

    SearchFriendStatusEnum(Integer status, String msg){
        this.status = status;
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public static String getMsgByKey(Integer status) {
        for (SearchFriendStatusEnum type : SearchFriendStatusEnum.values()) {
            if (type.getStatus() == status) {
                return type.msg;
            }
        }
        return null;
    }

}
