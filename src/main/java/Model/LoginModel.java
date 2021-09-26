package Model;

import lombok.Data;

@Data
public class LoginModel {
    public String userName;
    public String userMobile;
    public String userPassword;
    public boolean loginByMobile; // 使用手机号登录
}
