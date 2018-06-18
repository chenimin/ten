package ci.ten.controller;

import ch.qos.logback.core.status.StatusUtil;
import ci.ten.common.AjaxResult;
import ci.ten.common.StatusCodeConstants;
import ci.ten.service.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @RequestMapping("/index")
    public String index(){
        return "/register";
    }

    @RequestMapping("/account")
    @ResponseBody
    public AjaxResult registerAccount(String username,String password){
        Boolean registerAccount = registerService.registerAccount(username, password);
        if (!registerAccount){
            return new AjaxResult(StatusCodeConstants.USER_ALREADY_EXIST,"用户名已存在");
        }
        return new AjaxResult();
    }

}
