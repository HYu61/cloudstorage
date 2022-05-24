package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * author: Heng Yu
 */
@Repository
public interface CredentialMapper {
    @Insert("INSERT INTO CREDENTIALS VALUES(null, #{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    Integer insertCredential(Credential credential);

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId} ORDER BY credentialid")
    List<Credential> findAllByUserId(Integer userId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
    Optional<Credential> findCredentialById(Integer credentialId);

    @Update("UPDATE CREDENTIALS SET url= #{url}, username=#{username}, password=#{password} WHERE credentialid = #{credentialId}")
    Integer updateCredentialById(Credential updatedCredential);

    @Delete("DELETE FROM CREDENTIALS WHERE WHERE credentialid = #{credentialId}")
    Integer deleteCredentialById(Integer credentialId);
}
