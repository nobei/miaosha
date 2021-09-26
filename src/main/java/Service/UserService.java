package Service;

import Common.Constant;
import Dao.UserMapper;
import Data.User;
import Logger.LoggerConst;
import Model.LoginModel;
import Model.Token;
import Redis.RedisUtils;
import Utils.Md5Util;
import Utils.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.net.http.HttpResponse;
import java.util.UUID;

@Service
@Slf4j
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    RedisUtils redisUtils;

    final static String expireTime = "24*60*60*1000";

    public User selectUser(LoginModel login){
        if (login == null){
            return null;
        }

        if (login.isLoginByMobile()) {
            return userMapper.selectByName(login.getUserName());
        }else{
            return userMapper.selectByMobile(login.getUserMobile());
        }
    }

    public User login(LoginModel loginModel, HttpServletResponse response){
        User user = selectUser(loginModel);
        if(user == null || !user.getUserpassword()
                .equals(Md5Util.getMD5(loginModel.getUserPassword()))){
            return null;
        }
        Token token = createToken(expireTime);
        Cookie cookie = new Cookie(token.getToken(),token.getExpireTime());
        redisUtils.set(Constant.User,token.getToken(),token.getExpireTime());
        response.addCookie(cookie);
        log.info(LoggerConst.UserLogin.name(),user.getUsermobile()+user.getUsername()+"login in success");
        return user;
    }

    public Token createToken(String time){
        Token token = new Token();
        token.setToken(UuidUtil.getUUID());
        token.setExpireTime(time);
        return token;
    }

}
