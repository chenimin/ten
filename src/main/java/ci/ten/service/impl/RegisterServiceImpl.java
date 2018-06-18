package ci.ten.service.impl;

import ci.ten.common.Constants;
import ci.ten.dao.UserMapper;
import ci.ten.model.User;
import ci.ten.model.UserExample;
import ci.ten.service.RegisterService;
import ci.ten.service.UserService;
import ci.ten.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.channels.ReadableByteChannel;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

@Service
@Transactional
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;

    @Override
    public Boolean registerAccount(String username, String password) {
        //判断该用户是否已存在
        User user = userService.getUserAllByUsername(username);
        if (user != null){
            return false;
        }
        //随机生成salt
        Random r = new Random();
        StringBuilder salt = new StringBuilder(16);
        salt.append(r.nextInt(99999999)).append(r.nextInt(99999999));
        User registerUser = new User();
        registerUser.setCode(String.valueOf(UUID.randomUUID()));
        registerUser.setCreateTime(new Date());
       // registerUser.setPassword(MD5Utils.encode(password,salt.toString()));
        registerUser.setPassword(MD5Utils.passwordEncode(password,salt.toString()));
        registerUser.setRole(Constants.USER_ROLE_ADMIN);
        registerUser.setSalt(salt.toString());
        registerUser.setState(Constants.USER_STATE_NORMAL);
        registerUser.setUsername(username);
        registerUser.setDeleted(false);
        userMapper.insert(registerUser);
        return true;
    }
}
