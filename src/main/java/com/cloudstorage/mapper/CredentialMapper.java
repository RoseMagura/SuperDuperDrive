package com.cloudstorage.mapper;

import com.cloudstorage.model.Credential;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CredentialMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE url = #{url}")
    Credential getCredential(String url);

    @Insert("INSERT INTO CREDENTIALS (url, username, password, userid) " +
            "VALUES(#{url}, #{username}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    int createCredential(Credential credential);

    @Delete("DELETE FROM CREDENTIALS WHERE url = #{url}")
    void deleteCredential(String url);

    @Update("UPDATE credential set url = #{url}, username = #{username}, " +
            "password = #{password}, userid = #{userid}") //will userid change, though?
    void updateCredential(Credential credential);

}
