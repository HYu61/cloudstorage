package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * author: Heng Yu
 */
@Repository
public interface FileMapper {
    @Insert("INSERT INTO FILES VALUES(null, #{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insertFile(File file);

    @Select("SELECT * FROM FILES WHERE userid = #{userId} ORDER BY fileid ")
    List<File> findAllByUserId(Integer userId);

    @Select("SELECT * from FILES WHERE fileid = #{fileId} AND userid = #{loginUserId} LIMIT 1")
    Optional<File> findFileById(Integer fileId, Integer loginUserId);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId} AND userid = #{loginUserId}")
    Integer deleteFileById(Integer fileId, Integer loginUserId);
}
