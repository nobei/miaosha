package Dao;

import Data.User;
import org.springframework.stereotype.Repository;


@Repository
public interface UserMapper {
    int insert(User record);

    int insertSelective(User record);

    User selectByName(String name);

    User selectByMobile(String mobile);
}