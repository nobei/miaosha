package com.jwh.miaosha.Service;

import com.jwh.miaosha.Common.Constant;
import com.jwh.miaosha.Dao.UserMapper;
import com.jwh.miaosha.Data.User;
import com.jwh.miaosha.Logger.LoggerConst;
import com.jwh.miaosha.Model.LoginModel;
import com.jwh.miaosha.Model.Token;
import com.jwh.miaosha.Redis.RedisUtils;
import com.jwh.miaosha.Utils.CookieUtils;
import com.jwh.miaosha.Utils.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Provider;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;


@Service
@Slf4j
public class UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    Provider<RedisUtils> redisUtils;

    final static String expireTime = "86400000";

    public User selectUser(LoginModel login){
        if (login == null){
            return null;
        }

        if (login.isLoginByMobile()) {
            return userMapper.selectByMobile(login.getUserMobile());
        }else{
            return userMapper.selectByName(login.getUserName());
        }
    }

    public Boolean reigster(LoginModel register){
        User user = new User();
        if (register.isLoginByMobile()){
            user.setUsermobile(register.getUserMobile());
            user.setUserpassword(register.getUserPassword());
        }else{
            user.setUsername(register.getUserName());
            user.setUserpassword(register.getUserPassword());
        }
        int status = userMapper.insert(user);
        if (status == 0){
            return true;
        }else{
            return false;
        }

    }

    public User login(LoginModel loginModel, HttpServletResponse response){
        User user = selectUser(loginModel);
        if(user == null || !user.getUserpassword()
                .equals(loginModel.getUserPassword())){
            return null;
        }
        Token token = createToken(expireTime);
        Cookie cookie = CookieUtils.setCookie(Constant.User,token.getToken(),Integer.valueOf(expireTime));
        redisUtils.get().set(Long.valueOf(token.getExpireTime()),Constant.User,token.getToken(),user);
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
