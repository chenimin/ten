package ci.ten.config.shiro;

import ci.ten.model.User;
import ci.ten.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 自定义Realm
 */
public class UserRealm extends AuthorizingRealm {

    public static final Logger logger = LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private UserService userService;

    /**
     * 执行授权逻辑
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        logger.info("正在进行角色授权操作");
        //给资源进行授权
        //添加资源授权字符串
        Subject subject = SecurityUtils.getSubject();
        User user = (User)subject.getPrincipal();
        String role = user.getRole();
        logger.info("角色：｛｝",role);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取用户权限
        info.addStringPermission(role);
        return info;
    }

    /**
     * 执行认证逻辑
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        logger.info("正在执行认证逻辑操作");
        //判断用户名和密码是否正确
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        User user = userService.getUserByUsername(token.getUsername());
        if(user == null){
            //用户名不存在
            logger.info("用户不存在：｛｝",token.getUsername());
            return null;//Shiro底层会抛出UnknownAccountException
        }
        return new SimpleAuthenticationInfo(user,user.getPassword(),ByteSource.Util.bytes(user.getSalt()),getName());
    }

    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        // 重写 setCredentialsMatcher 方法为自定义的 Realm 设置 hash 验证方法
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1024);
        super.setCredentialsMatcher(hashedCredentialsMatcher);
    }


}
