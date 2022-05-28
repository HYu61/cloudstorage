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

    @Select("SELECT * FROM CREDENTIALS WHERE userid = #{loginUserId} ORDER BY credentialid")
    List<Credential> findAllByUserId(Integer loginUserId);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId} AND userid = #{loginUserId} LIMIT 1")
    Optional<Credential> findCredentialById(Integer credentialId, Integer loginUserId);

    @Update("UPDATE CREDENTIALS SET password=#{credential.password} WHERE credentialid = #{credential.credentialId} AND userid = #{loginUserId}")
    Integer updateCredentialById(Credential credential, Integer loginUserId);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId} AND userid = #{loginUserId}")
    Integer deleteCredentialById(Integer credentialId, Integer loginUserId);
}
