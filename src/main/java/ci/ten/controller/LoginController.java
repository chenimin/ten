package ci.ten.controller;

import ci.ten.common.AjaxResult;
import ci.ten.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
    @Autowired
    private UserService userService;


    @RequestMapping("/login")
    public String login(Model model){
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "/login";
    }

    @RequestMapping("/check")
    @ResponseBody
    public AjaxResult check(String  username,String password,Boolean rememberMe){

        /**
         * 使用Shiro编写认证操作
         */
        //1.获取Subject
        Subject subject = SecurityUtils.getSubject();
        //2.封装用户数据
        UsernamePasswordToken token = new UsernamePasswordToken(username,password, rememberMe==null?false:rememberMe);
        //3.执行登录方法
        try{
            subject.login(token);
            //不报错，不抛异常代表登陆成功
        }catch (UnknownAccountException e){
            return new AjaxResult(1000,"用户名不存在");
          //  return "login";
        }catch (IncorrectCredentialsException e){
            return new AjaxResult(1001,"密码错误");
        }
        return new AjaxResult("登陆成功！");
    }

    @RequestMapping("/unauthorized")
    public String unAuthorized(){
        return "/unauthorized";
    }

}
