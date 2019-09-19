package com.rjl.service;

import com.rjl.enums.SearchFriendStatusEnum;
import com.rjl.mapper.FriendsListMapper;
import com.rjl.mapper.FriendsRequestMapper;
import com.rjl.org.n3r.idworker.Sid;
import com.rjl.pojo.FriendsList;
import com.rjl.pojo.FriendsRequest;
import com.rjl.service.UserService;
import com.rjl.mapper.UsersMapper;
import com.rjl.pojo.Users;
import com.rjl.utils.FastDFSClient;
import com.rjl.utils.FileUtils;
import com.rjl.utils.QRCodeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import java.io.IOException;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private FriendsListMapper friendsListMapper;

    @Autowired
    private FriendsRequestMapper friendsRequestMapper;
    @Autowired
    private Sid sid;

    @Autowired
    private QRCodeUtils qrCodeUtils;

    @Autowired
    private FileUtils fileUtils;

    @Autowired
    private FastDFSClient fastDFSClient;
    @Override
    public boolean queryUsernameIsExist(String username) {
        Users user = new Users();
        user.setUsername(username);
        Users result = usersMapper.selectOne(user);


        return result != null ? true:false;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Users queryUserForLogin(String username, String pwd) {
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username",username);
        criteria.andEqualTo("password",pwd);
        Users result = usersMapper.selectOneByExample(userExample);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Users saveUser(Users user) {
        String userId = sid.nextShort();
        // make a unique QR Code for every user

        String qrCodePath = "//Users/RJ/Desktop" + userId + "qrcode.png";
        // laichat_qrcode:[username]
        qrCodeUtils.createQRCode(qrCodePath,"laichat_qrcode:" + user.getUsername());
        MultipartFile qrCodeFile = fileUtils.fileToMultipart(qrCodePath);

        String qrCodeUrl ="";
        try {
            qrCodeUrl = fastDFSClient.uploadQRCode(qrCodeFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        user.setQrcode(qrCodeUrl);

        user.setId(userId);
        usersMapper.insert(user);
        return user;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Users updateUserInfo(Users user) {
        usersMapper.updateByPrimaryKeySelective(user);
        return queryUserById(user.getId());
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Users queryUserById(String userId) {
        return usersMapper.selectByPrimaryKey(userId);
    }

    @Override
    public Integer preconditionSearchFriends(String myUserId, String friendUsername) {


        Users user = queryUserInfoByUsername(friendUsername);

        // precondition - 1. if user doesn't exist, return user DNE
        if (user == null) {
            return SearchFriendStatusEnum.USER_NOT_EXIST.status;
        }

        // precondition - 2. if user is yourself, return you cant add yourself
        if (user.getId().equals(myUserId)) {
            return SearchFriendStatusEnum.NOT_YOURSELF.status;
        }

        // precondition - 3. if user is in your friends, return user is already your friend.
        Example mfe = new Example(FriendsList.class);
        Example.Criteria mfc = mfe.createCriteria();
        mfc.andEqualTo("myUserId", myUserId);
        mfc.andEqualTo("myFriendUserId", user.getId());
        FriendsList myFriendsRel = friendsListMapper.selectOneByExample(mfe);
        if (myFriendsRel != null) {
            return SearchFriendStatusEnum.ALREADY_FRIENDS.status;
        }

        return SearchFriendStatusEnum.SUCCESS.status;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users queryUserInfoByUsername(String username) {
        Example ue = new Example(Users.class);
        Example.Criteria uc = ue.createCriteria();
        uc.andEqualTo("username", username);
        return usersMapper.selectOneByExample(ue);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void sendFriendRequest(String myUserId, String friendUsername) {

        // get informartion by username
        Users friend = queryUserInfoByUsername(friendUsername);

        // 1. check add friend request record.
        Example fre = new Example(FriendsRequest.class);
        Example.Criteria frc = fre.createCriteria();
        frc.andEqualTo("sendUserId", myUserId);
        frc.andEqualTo("acceptUserId", friend.getId());
        FriendsRequest friendRequest = friendsRequestMapper.selectOneByExample(fre);
        if (friendRequest == null) {
            // 2. if user is not in your friendlist, add he request record to database
            String requestId = sid.nextShort();

            FriendsRequest request = new FriendsRequest();
            request.setId(requestId);
            request.setRequestUserId(myUserId);
            request.setAcceptUserId(friend.getId());
            request.setRequstDateTime(new Date());
            friendsRequestMapper.insert(request);
        }
    }

}
