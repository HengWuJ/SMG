package com.smg.user.mapper;

import com.smg.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.*;

import java.util.List;


@Mapper
public interface UserMapper {

    @Select("SELECT * FROM user WHERE id = #{id}")
    User findById(@Param("id") Integer id);

    @Select("SELECT * FROM user")
    List<User> findAll();

    @Insert("INSERT INTO user(username, password, email, full_name, phone_number, role, status, profile_picture) " +
            "VALUES(#{username}, #{password}, #{email}, #{fullName}, #{phoneNumber}, #{role}, #{status}, #{profilePicture})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insert(User user);

    @Insert("INSERT INTO user(username, password, email, full_name, phone_number, role, status, profile_picture, face_token) " +
            "VALUES(#{username}, #{password}, #{email}, #{fullName}, #{phoneNumber}, #{role}, #{status}, #{profilePicture}, #{faceToken})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertWithToken(User user);

    @Update("UPDATE user SET username=#{username}, password=#{password}, email=#{email}, full_name=#{fullName}, " +
            "phone_number=#{phoneNumber}, role=#{role}, status=#{status}, profile_picture=#{profilePicture} WHERE id=#{id}")
    void update(User user);

    @Delete("DELETE FROM user WHERE id = #{id}")
    void deleteById(@Param("id") Integer id);

    @Update("UPDATE user SET status = #{status} WHERE id = #{id}")
    void changeStatus(@Param("id") Integer id, @Param("status") String status);

    @Update("UPDATE user SET password = #{newPassword} WHERE id = #{id}")
    void resetPassword(@Param("id") Integer id, @Param("newPassword") String newPassword);

    @Select("SELECT * FROM user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);
}
