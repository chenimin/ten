package ci.ten.service;

import ci.ten.model.User;

public interface UserService {

    public User getUserAllByUsername(String username);

    public User getUserByUsername(String username);

    public User getUserById(Long id);

}
