package ci.ten.controller;

import ci.ten.common.AjaxResult;
import ci.ten.common.Pageable;
import ci.ten.common.Pagination;
import ci.ten.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @RequestMapping("/index")
    public String index(){
        return "auth_index";
    }

    @RequestMapping("/page")
    @ResponseBody
    public AjaxResult getUserAuthPage(Pageable pageable){
        Pagination page = authService.getUserAuthPage(pageable);
        return new AjaxResult(page);
    }

}
