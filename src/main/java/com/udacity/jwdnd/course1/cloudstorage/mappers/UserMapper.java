package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.User;
import lombok.Setter;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * author: Heng Yu
 */
@Repository
public interface UserMapper {
    @Insert("INSERT INTO USERS VALUES(null, #{username}, #{salt}, #{password},#{firstName}, #{lastName});")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    Integer insertUser(User user);

    @Select("SELECT * FROM USERS WHERE username = #{username} LIMIT 1;")
    Optional<User> selectUserByUsername(String username);



}
