package com.rjl.service;

import com.rjl.org.n3r.idworker.Sid;
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

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

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
}
