package ci.ten.service.impl;

import ci.ten.common.Constants;
import ci.ten.config.exception.multipleUsersException;
import ci.ten.dao.UserMapper;
import ci.ten.model.User;
import ci.ten.model.UserExample;
import ci.ten.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserAllByUsername(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andUsernameEqualTo(username).andDeletedEqualTo(false);
        List<User> users = userMapper.selectByExample(example);
        if (users.size() == 0){
            return null;
        }
        if (users.size() > 1){
            throw new multipleUsersException("存在多个相同的用户："+username);
        }
        return users.get(0);
    }

    @Override
    public User getUserByUsername(String username) {
        UserExample example = new UserExample();
        example.createCriteria().andStateEqualTo(Constants.USER_STATE_NORMAL)
                .andUsernameEqualTo(username).andDeletedEqualTo(false);
        List<User> users = userMapper.selectByExample(example);
        if (users.size() == 0){
            return null;
        }
        if (users.size() > 1){
            throw new multipleUsersException("存在多个相同的用户："+username);
        }
        return users.get(0);
    }

    @Override
    public User getUserById(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }
}
