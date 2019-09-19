package com.rjl.service;


import com.rjl.pojo.Users;
import org.springframework.stereotype.Service;

public interface UserService {
    /**
     * check if username exist
     * @param username
     * @return
     */
    public boolean queryUsernameIsExist(String username);

    /**
     * @Description: check log in authentication
     */
    public Users queryUserForLogin(String username, String pwd);

    /**
     * @Description: save user to database
     */
    public Users saveUser(Users user) ;

    public Users updateUserInfo(Users user) ;

    /**
     * @Description: search user by user ID
     */
    public Users queryUserById(String userId) ;
    /**
     * @Description: search friends precondition
     */
    public Integer preconditionSearchFriends(String myUserId, String friendUsername);

    /**
     * @Description: search user by username
     */
    public Users queryUserInfoByUsername(String username);

    /**
     * @Description: add friend request record to database
     */
    public void sendFriendRequest(String myUserId, String friendUsername);

}
