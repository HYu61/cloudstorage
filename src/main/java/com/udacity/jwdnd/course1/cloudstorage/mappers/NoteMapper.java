package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

/**
 * author: Heng Yu
 */
public interface NoteMapper {
    @Insert("INSERT INTO FILES VALUES(null, #{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer insertNote(Note note);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId} ORDER BY noteid")
    List<Note> findAllByUserId(Integer userId);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId}")
    Optional<Note> findNoteById(Integer noteId);

    @Update("UPDATE NOTES SET notetitle= #{noteTitle}, notedescription=#{noteDescription} WHERE noteid = #{noteId}")
    Integer updateNoteById(Note updatedNote);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    Integer deleteNoteById(Integer noteId);
}
