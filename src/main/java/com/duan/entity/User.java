package com.duan.entity;

import lombok.Data;


@Data
/**
 * 用户实体类对应数据库表中的t_user
 */
public class User extends BaseEntity{
    private Integer uid;
    private String username;
    private String password;
    private String salt;
    private String phone;
    private String email;
    private Integer gender;
    private String avatar;
    private Integer isDelete;

}
