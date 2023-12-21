package com.duan.Mapper;

import com.duan.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.Date;

@Mapper
public interface UserMapper {
    /**
     * 根据用户名查询
     * @param username 用户名
     * @return 根据用户名查出的用户
     */
    @Select("select * from t_user where username =#{username}")
    User SelectByName(@Param("username") String username);

    /**
     * 增加用户(注册)
     * @param user User对象
     * @return  插入的行数
     */
    @Insert("insert into t_user VALUES (#{uid},#{username}, #{password}, #{salt}, #{phone}, #{email}, #{gender}, " +
            "#{avatar}, #{isDelete}, #{createdUser}, #{createdTime}, #{modifiedUser}, #{modifiedTime})")
    int insert(User user);

    /**
     * 根据username查询password(登录)
     * 先根据用户名查询是否存在账户，再对账户进行判断
     */
    @Select("select * from  t_user where username = #{username}")
    User login(@Param("username") String username);

    /**
     * 根据uid修改密码
     * @param uid
     * @param password
     * @param modifiedUser
     * @param modifiedTime
     * @return 受影响的行数
     */
    @Update("update t_user set password = #{password}, modified_user = #{modifiedUser}, modified_time = #{modifiedTime} where uid = #{uid}")
    Integer updatePasswordByUid(@Param("uid") Integer uid, @Param("password") String password,
                                @Param("modifiedUser") String  modifiedUser,@Param("modifiedTime") Date modifiedTime);

    /**
     * 根据id查询用户
     * @param uid
     * @return
     */
    @Select("select * from t_user where uid = #{uid}")
    User findById(@Param("uid") Integer uid);

    /**
     * 根据uid查询用户
     * @param user
     * @return
     */
    Integer updateUserByUid(User user);

    /**
     * 根据uid修改头像路径
     * @param uid uid
     * @param avatar 头像路径
     * @param modifiedUser 操作用户
     * @param modifiedTime 操作时间
     * @return
     */
    @Update(("update t_user set avatar = #{avatar}, modified_user = #{modifiedUser}," +
            "modified_time = #{modifiedTime} where uid = #{uid}"))
    Integer updateAvatarByUid(@Param("uid") Integer uid, @Param("avatar") String avatar,@Param("modifiedUser") String modifiedUser,
                              @Param("modifiedTime") Date modifiedTime);
}

