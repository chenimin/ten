package ci.ten.service.impl;

import ci.ten.dao.UserMapper;
import ci.ten.model.User;
import ci.ten.model.UserExample;
import ci.ten.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserbyUsername(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andStateEqualTo(1).andUsernameEqualTo(username);
        List<User> users = userMapper.selectByExample(example);
        if (users == null){
            return null;
        }
        if (users.size() > 1){
            throw new RuntimeException("存在多个相同的用户名："+username);
        }
        return users.get(0);
    }
}
