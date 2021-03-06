package ci.ten.config.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import ci.ten.config.redis.RedisSessionDao;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {

    @Autowired
    RedisSessionDao sessionDao;

    /**
     * 创建ShiroFilterFactoryBean
     */
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //设置安全管理器
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //添加shiro内置过滤器
        /**
         * 常用内置过滤器
         *  anon:无需认证（登录）可以访问
         *  authc：必须认证（登录）才能访问
         *  user：如果使用remember me的功能可以直接访问
         *  perms：该资源必须得到资源权限才可以访问
         *  role：该资源必须得到角色权限才可以访问
         */
        Map<String,String> filterMap = new LinkedHashMap<>();
        //需要拦截的资源地址，必须认证（登录）才能访问
        //filterMap.put("/logout", "logout");
        filterMap.put("/err","anon");     //放行error请求
        filterMap.put("/login","anon");     //放行login请求
        filterMap.put("/check","anon");     //放行check请求
        //放行资源请求
        filterMap.put("/css/*","anon");
        filterMap.put("/fonts/*","anon");
        filterMap.put("/images/*","anon");
        filterMap.put("/js/*","anon");
        filterMap.put("/lib/*","anon");
        filterMap.put("/unauthorized","anon");     //放行unauthorized请求
        filterMap.put("/register/*","anon");     //放行register请求
        filterMap.put("/favicon.ico", "anon");
        filterMap.put("/*","authc");   //意为目录下所有资源都必须认证（登录）才能访问
        //filterMap.put("/logout", "logout");
        //授权过滤器
        //注意：当授权拦截之后，Shiro会自动跳转到未授权页面
       // filterMap.put("/index","perms[admin]");
        //设置Shiro需要跳转到的未授权提示页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");
        //修改跳转的页面
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/index");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return  shiroFilterFactoryBean;
    }

    /**
     * 创建DefaultWebSecurityManager
     */
    @Bean(name = "securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("userRealm") UserRealm userRealm){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //关联Realm
        securityManager.setRealm(userRealm);
        securityManager.setSessionManager(sessionManager());
        return  securityManager;
    }
    /**
     * 创建Realm
     */
    @Bean(name = "userRealm")
    public UserRealm getRealm(){
        return new UserRealm();
    }

    /**
     * 配置ShiroDialect，用于thymeleaf和Shiro标签配合使用
     */
    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }


    @Bean
    public SessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(sessionDao);
//        sessionManager.setGlobalSessionTimeout(1800);
//        SecurityUtils.getSubject().getSession().setTimeout(-1000l);
        return sessionManager;
    }



}
