package com.jwh.miaosha.Controller;

import com.jwh.miaosha.Data.User;
import com.jwh.miaosha.Model.LoginModel;
import com.jwh.miaosha.Model.ResponseModel;
import com.jwh.miaosha.Model.ResponseStatus;
import com.jwh.miaosha.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/User")
public class UserController {
    @Autowired
    UserService userService;

    @RequestMapping("/loginPage")
    public String loginPage() {
        return "login";
    }


    @RequestMapping(value = "/do_login",method = RequestMethod.POST)
    @ResponseBody
    public ResponseModel<User> login(@RequestBody LoginModel login, HttpServletResponse response) {
        if (!validRequest(login)) {
            return new ResponseModel<>(null, ResponseStatus.Failed);
        }
        User user = userService.login(login, response);
        if (user == null) {
            return new ResponseModel<>(null, ResponseStatus.Failed);
        } else {
            return new ResponseModel<User>(user, ResponseStatus.Success);
        }


    }

    @RequestMapping("/do_register")
    @ResponseBody
    public ResponseModel<Boolean> register(@RequestBody LoginModel register){
        if(!validRequest(register)){
            return new ResponseModel<>(null, ResponseStatus.Failed);
        }
        Boolean reigsterStatus = userService.reigster(register);
        if (reigsterStatus) {
            return new ResponseModel<>(null, ResponseStatus.Failed);
        } else {
            return new ResponseModel<>(null, ResponseStatus.Success);
        }

    }


    private boolean validRequest(LoginModel loginModel) {
        if (loginModel == null || (StringUtils.isEmpty(loginModel.userMobile)
                && StringUtils.isEmpty(loginModel.userName))
                || StringUtils.isEmpty(loginModel.userPassword)) {
            return false;
        }

        if (!StringUtils.isEmpty(loginModel.userMobile)) {
            loginModel.setLoginByMobile(true);
        } else {
            loginModel.setLoginByMobile(false);
        }

        return true;

    }
}
