package com.rjl.controller;


import com.rjl.enums.SearchFriendStatusEnum;
import com.rjl.pojo.Users;
import com.rjl.pojo.bo.UsersBO;
import com.rjl.pojo.vo.UsersVo;
import com.rjl.service.UserService;
import com.rjl.utils.FastDFSClient;
import com.rjl.utils.FileUtils;
import com.rjl.utils.JSONResult;
import com.rjl.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FastDFSClient fastDFSClient;

    @PostMapping("/registerOrLogin")
    public JSONResult registerOrLogin(@RequestBody Users user) throws Exception {
        System.err.println("connecting resgisterOrLogin");
        // validate username and passowrd
        if(StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())){
            return JSONResult.errorMsg("username or password can't be blank");
        }

        //check if username exists, if not then register
        boolean usernameIsExist = userService.queryUsernameIsExist(user.getUsername());
        System.out.println(usernameIsExist);
        Users userResult = null;
        if (usernameIsExist) {
            //login
            userResult = userService.queryUserForLogin(user.getUsername(),MD5Utils.getMD5Str(user.getPassword()));
            if (userResult == null) {
                return JSONResult.errorMsg("username or password incorrect");
            }

        } else {
            //register
            user.setNickname(user.getUsername());
            user.setIconImage("");
            user.setIconImageBig("");

            try {
                user.setPassword(MD5Utils.getMD5Str(user.getPassword()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            userResult = userService.saveUser(user);

        }
        UsersVo usersVo = new UsersVo();
        BeanUtils.copyProperties(userResult,usersVo);
        return JSONResult.ok(usersVo);
    }
    @PostMapping("/uploadIconBase64")
    public JSONResult uploadIconBase64(@RequestBody UsersBO userBo) throws Exception {

        System.err.println("uploadIconBase64");
        //obtain the base64 string from frontend then convert to file then upload
        String base64Data = userBo.getIconData();
        String userIconPath = "C:\\" + userBo.getUserId() + "userIcon64.png";
        FileUtils.base64ToFile(userIconPath,base64Data);

        // upload file to fastdfs
        MultipartFile icon = FileUtils.fileToMultipart(userIconPath);
        String url = fastDFSClient.uploadBase64(icon);
        System.out.println(url);

        //obtain thumpimage's url
        String thump = "_80x80.";
        String arr[] = url.split("\\.");
        String thumpImgUrl = arr[0] + thump +arr[1];

        //update user's icon
        Users user = new Users();
        user.setId(userBo.getUserId());
        user.setIconImage(thumpImgUrl);
        user.setIconImageBig(url);

        Users result = userService.updateUserInfo(user);

        return JSONResult.ok(result);
    }

    @PostMapping("/setNickname")
    public JSONResult setNickname(@RequestBody UsersBO userBo) throws Exception {

        Users user = new Users();
        user.setId(userBo.getUserId());
        user.setNickname(userBo.getNickname());


        Users result = userService.updateUserInfo(user);

        return JSONResult.ok(result);

    }
    /**
     * @Description: 发送添加好友的请求
     */
    @PostMapping("/addFriendRequest")
    public JSONResult addFriendRequest(String myUserId, String friendUsername)
            throws Exception {

        // 0. 判断 myUserId friendUsername 不能为空
        if (StringUtils.isBlank(myUserId)
                || StringUtils.isBlank(friendUsername)) {
            return JSONResult.errorMsg("");
        }


        // precondition - 1. if user doenst exist, return user DNE
        // precondition - 2. if user is yuorself, return you cant add yourself
        // precondition - 3. if user is in your friends, return user is already your friend.
        Integer status = userService.preconditionSearchFriends(myUserId, friendUsername);
        if (status == SearchFriendStatusEnum.SUCCESS.status) {
            userService.sendFriendRequest(myUserId, friendUsername);
        } else {
            String errorMsg = SearchFriendStatusEnum.getMsgByKey(status);
            return JSONResult.errorMsg(errorMsg);
        }

        return JSONResult.ok();
    }
    /**
     * @Description" search friend api
     */
    @PostMapping("/search")
    public JSONResult searchUser(String myUserId, String friendUsername)
            throws Exception {

        // 0. 判断 myUserId friendUsername 不能为空
        if (StringUtils.isBlank(myUserId)
                || StringUtils.isBlank(friendUsername)) {
            return JSONResult.errorMsg("");
        }

        // precondition - 1. if user doenst exist, return user DNE
        // precondition - 2. if user is yuorself, return you cant add yourself
        // precondition - 3. if user is in your friends, return user is already your friend.
        Integer status = userService.preconditionSearchFriends(myUserId, friendUsername);
        if (status == SearchFriendStatusEnum.SUCCESS.status) {
            Users user = userService.queryUserInfoByUsername(friendUsername);
            UsersVo userVO = new UsersVo();
            BeanUtils.copyProperties(user, userVO);
            return JSONResult.ok(userVO);
        } else {
            String errorMsg = SearchFriendStatusEnum.getMsgByKey(status);
            return JSONResult.errorMsg(errorMsg);
        }
    }

}
