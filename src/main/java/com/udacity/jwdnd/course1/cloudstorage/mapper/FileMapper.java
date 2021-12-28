package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    File getFileByName(String filename);

    @Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
    File getFileById(Integer fileId);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{filename}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int storeFile(File file);

    @Select("SELECT * FROM FILES WHERE userid= #{userid} ")
    List<File> getAllFiles(Integer userid);

    @Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
    int deleteFile(int fileId);

    @Select("SELECT * FROM FILES WHERE userid = #{userid} AND filename = #{filename}")
    File getFile(Integer userid, String filename);
}
