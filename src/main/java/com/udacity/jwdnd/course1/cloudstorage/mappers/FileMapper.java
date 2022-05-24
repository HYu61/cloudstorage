package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Optional;

/**
 * author: Heng Yu
 */
public interface FileMapper {
    @Insert("INSERT INTO FILES VALUES(null, #{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    Integer insertFile(File file);

    @Select("SELECT * FROM FILES WHERE userid = #{userId} ORDER BY fileid ")
    List<File> findAllByUserId(Integer userId);

    @Select("SELECT * from FILES WHERE fileid = #{fileId}")
    Optional<File> findFileById(Integer fileId);

    @Delete("DELETE FROM FILES WHERE filedid = #{fileId}")
    Integer deleteFileById(Integer fileId);
}
