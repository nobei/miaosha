package Controller;

import Common.Constant;
import Data.User;
import Model.LoginModel;
import Model.ResponseModel;
import Model.ResponseStatus;
import Redis.RedisUtils;
import Service.UserService;
import Utils.CookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/User")
@Controller
public class UserController {
    @Autowired
    UserService userService;


    @RequestMapping("/login")
    @ResponseBody
    public ResponseModel<User> login(LoginModel login, HttpServletResponse response) {
        if (!validRequest(login)) {
            return new ResponseModel<>(null, ResponseStatus.Failed);
        }
        User user = userService.login(login,response);
        if (user == null){
            return new ResponseModel<>(null, ResponseStatus.Failed);
        }else{
            return new ResponseModel<User>(user,ResponseStatus.Success );
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
