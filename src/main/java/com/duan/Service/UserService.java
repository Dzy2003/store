package com.duan.Service;

import com.duan.entity.User;

public interface UserService {
    void reg(User user);

    User login(String username, String password);

    void changePassword(Integer uid,String username,String oldPassword,String newPassword);

    User getById(Integer uid);

    void changeInfo(Integer uid,String username,User user);

    void changeAvatar(Integer uid,String username,String avatar);
}