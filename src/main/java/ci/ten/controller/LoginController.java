package ci.ten.controller;

import ci.ten.common.AjaxResult;
import ci.ten.model.User;
import ci.ten.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private LoginService loginService;


    @RequestMapping("/index")
    public String login(){
        return "login";
    }

    @RequestMapping("/check")
    @ResponseBody
    public AjaxResult check(String  username,String password){
        User user = loginService.getUserbyUsername(username);
        if (user == null){
            return new AjaxResult(400,"找不到该用户");
        }
        return new AjaxResult();
    }

}
