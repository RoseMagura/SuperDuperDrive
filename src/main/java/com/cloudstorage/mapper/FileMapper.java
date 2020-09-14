package com.cloudstorage.mapper;

import com.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES")
    List<File> getAll();

    @Select("SELECT * FROM FILES WHERE fileid = #{fileId}")
    File getFile(String fileId);

    @Select("SELECT * FROM FILES WHERE userid = #{userId}")
    List<File> getAllByUser(Integer userId);

    @Select("SELECT * FROM FILES WHERE filename = #{filename}")
    File getFileByName(String filename);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{filename}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int createFile(File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
    void deleteFile(String fileId);

    @Update("UPDATE FILES set filename = #{filename}, contenttype = #{contentType}," +
            "filesize = #{fileSize}, userid = #{userId}, filedata = #{fileData} WHERE fileid = #{fileId}")
    void updateFile(File file);
}
