package com.duan.Controller;

import com.duan.Controller.Exception.BusinessException;
import com.duan.Service.UserService;
import com.duan.entity.User;
import com.duan.util.R;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService service;
    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping()
    R<Void> reg(@RequestBody User user){
        service.reg(user);
        return R.success(null);
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return
     */
    @PostMapping("/login")
    R<User> login(@RequestParam String username,@RequestParam String password,HttpSession session){
        User user = service.login(username, password);
        session.setAttribute("uid", user.getUid());
        session.setAttribute("username", user.getUsername());
        return R.success(user);
    }

    /**
     * 控制层：修改密码
     * @param oldPassword
     * @param newPassword
     * @param session
     * @return
     */
    @PutMapping("/change_password")
    R<Void> changePassword(@RequestParam String oldPassword, @RequestParam String newPassword, HttpSession session){
        // 调用session.getAttribute("")获取uid和username
        Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
        String username =  session.getAttribute("username").toString();
        service.changePassword(uid,username,oldPassword,newPassword);
        return R.success(null);
    }

    /**
     * 获取用户资料
     * @param session
     * @return R
     */
    @GetMapping()
    R<User> getUser(HttpSession session){
        Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
        User user = service.getById(uid);
        return R.success(user);
    }

    /**
     * 修改用户资料
     * @param user
     * @param session
     * @return R
     */
    @PutMapping()
    R<Void> changeInfo(@RequestBody User user, HttpSession session){
        Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
        String username =  session.getAttribute("username").toString();
        service.changeInfo(uid,username,user);
        return  R.success(null);
    }

    /** 头像文件大小的上限值(10MB) */
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    /** 允许上传的头像的文件类型 */
    public static final List<String> AVATAR_TYPES = new ArrayList<>();

    /** 初始化允许上传的头像的文件类型 */
    static {
        AVATAR_TYPES.add("image/jpeg");
        AVATAR_TYPES.add("image/png");
        AVATAR_TYPES.add("image/bmp");
        AVATAR_TYPES.add("image/gif");
    }

    @PostMapping("change_avatar")
    public R<String> changeAvatar(@RequestParam("file") MultipartFile file, HttpSession session) {
        // 判断上传的文件是否为空
        if (file.isEmpty()) {
            // 是：抛出异常
            throw new BusinessException("上传的头像文件不允许为空");
        }

        // 判断上传的文件大小是否超出限制值
        if (file.getSize() > AVATAR_MAX_SIZE) { // getSize()：返回文件的大小，以字节为单位
            // 是：抛出异常
            throw new BusinessException("不允许上传超过" + (AVATAR_MAX_SIZE / 1024) + "KB的头像文件");
        }

        // 判断上传的文件类型是否超出限制
        String contentType = file.getContentType();
        // public boolean list.contains(Object o)：当前列表若包含某元素，返回结果为true；若不包含该元素，返回结果为false。
        if (!AVATAR_TYPES.contains(contentType)) {
            // 是：抛出异常
            throw new BusinessException("不支持使用该类型的文件作为头像，允许的文件类型：\n" + AVATAR_TYPES);
        }

        // 获取当前项目的绝对磁盘路径
        String parent = session.getServletContext().getRealPath("upload");
        // 保存头像文件的文件夹
        File dir = new File(parent);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        // 保存的头像文件的文件名
        String suffix = "";
        String originalFilename = file.getOriginalFilename();
        int beginIndex = originalFilename.lastIndexOf(".");
        if (beginIndex > 0) {
            suffix = originalFilename.substring(beginIndex);
        }
        String filename = UUID.randomUUID() + suffix;

        // 创建文件对象，表示保存的头像文件
        File dest = new File(dir, filename);
        // 执行保存头像文件
        try {
            file.transferTo(dest);
        } catch (IllegalStateException e) {
            // 抛出异常
            throw new BusinessException("文件状态异常，可能文件已被移动或删除");
        } catch (BusinessException e) {
            // 抛出异常
            throw new BusinessException("上传文件时读写错误，请稍后重尝试");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 头像路径
        String avatar ="/upload/" + filename;
        System.out.println(parent+avatar);
        // 从Session中获取uid和username
        Integer uid = (Integer) session.getAttribute("uid");
        String username = session.getAttribute("username").toString();
        // 将头像写入到数据库中
        service.changeAvatar(uid, username, avatar);

        // 返回成功头像路径
        return R.success(avatar);
    }
}
