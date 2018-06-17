package ci.ten.controller;

import ci.ten.common.AjaxResult;
import ci.ten.model.User;
import ci.ten.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
    @Autowired
    private LoginService loginService;


    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/check")
    @ResponseBody
    public AjaxResult check(String  username,String password){
        User user = loginService.getUserbyUsername(username);
        if (user == null){
            return new AjaxResult(400,"用户名或密码错误");
        }
        return new AjaxResult("登陆成功！");
    }

    @RequestMapping("/index")
    public String index(){
        return "index";
    }

}
