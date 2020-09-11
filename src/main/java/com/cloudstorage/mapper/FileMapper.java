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

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{fileName}, #{fileSize}, #{userId}), #{fileData}")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int createFile(File file);

    @Delete("DELETE FROM FILES WHERE fileid = #{fileId}")
    void deleteFile(String fileId);

    @Update("UPDATE FILES set filename = #{fileName}, contenttype = #{contentType}," +
            "filesize = #{fileSize}, userid = #{userId}, filedata = #{fileData} WHERE fileid = #{fileId}")
    void updateFile(File file);
}
