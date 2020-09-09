package com.cloudstorage.mapper;

import com.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE filename = #{fileName}")
    File getFile(String fileName);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) " +
            "VALUES(#{fileName}, #{fileSize}, #{userId}), #{fileData}")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int createFile(File file);

    @Delete("DELETE FROM FILES WHERE filetitle = #{fileTitle}")
    void deleteFile(String fileTitle);

    @Update("UPDATE file set filename = #{fileName}, contenttype = #{contentType}," +
            "filesize = #{fileSize}, userid = #{userId}, filedata = #{fileData}")
    void updateFile(File file);
}
