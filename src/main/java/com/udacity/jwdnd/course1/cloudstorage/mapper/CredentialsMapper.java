package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.entity.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE userid= #{userid}")
    List<Credentials> getCredentials(Integer userid);

    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES (#{url}, #{username}, #{key}, #{password},#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    void insertCredentilas(Credentials credentials);

    @Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username}, key = #{key}, password= #{password} WHERE credentialid = #{credentialid}")
    void updateCredentilas(Credentials credentials);

    @Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    int deleteCredentilas(int credentialid);

    @Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialid}")
    Credentials getCredentialById(Integer credentialid);
}
