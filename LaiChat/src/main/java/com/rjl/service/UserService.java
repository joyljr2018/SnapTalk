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


    public Users queryUserForLogin(String username, String pwd);

    public Users saveUser(Users user) ;
    public Users updateUserInfo(Users user) ;
    public Users queryUserById(String userId) ;
}
