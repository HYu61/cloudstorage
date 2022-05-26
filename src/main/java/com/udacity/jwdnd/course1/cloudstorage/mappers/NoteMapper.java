package com.udacity.jwdnd.course1.cloudstorage.mappers;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * author: Heng Yu
 */
@Repository
public interface NoteMapper {
    @Insert("INSERT INTO NOTES VALUES(null, #{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    Integer insertNote(Note note);

    @Select("SELECT * FROM NOTES WHERE userid = #{userId} ORDER BY noteid")
    List<Note> findAllByUserId(Integer userId);

    @Select("SELECT * FROM NOTES WHERE noteid = #{noteId} AND userid = #{loginUserId}")
    Optional<Note> findNoteById(Integer noteId, Integer loginUserId);

    @Update("UPDATE NOTES SET notetitle= #{note.noteTitle}, notedescription=#{note.noteDescription} WHERE noteid = #{note.noteId}AND userid = #{loginUserId}")
    Integer updateNoteById(Note note,Integer loginUserId);

    @Delete("DELETE FROM NOTES WHERE noteid = #{noteId}")
    Integer deleteNoteById(Integer noteId);
}
