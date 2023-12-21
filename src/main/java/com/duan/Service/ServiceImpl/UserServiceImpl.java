package com.duan.Service.ServiceImpl;

import com.duan.Controller.Exception.BusinessException;
import com.duan.Controller.Exception.SystemException;
import com.duan.Mapper.UserMapper;
import com.duan.Service.UserService;
import com.duan.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper mapper;
    //Todo 注册
    @Override
    public void reg(User user) {

        User user1 = mapper.SelectByName(user.getUsername());
        if(user1 != null){
            //抛出用户名被占用异常
            throw new BusinessException("用户名已经被使用，请重新输入！");
        }
        Date now = new Date();
        // 补全数据：加密后的密码
        String salt = UUID.randomUUID().toString().toUpperCase();
        String md5Password = getMd5Password(user.getPassword(), salt);
        user.setPassword(md5Password);
        // 补全数据：盐值
        user.setSalt(salt);
        // 补全数据：isDelete(0)
        user.setIsDelete(0);
        // 补全数据：4项日志属性
        user.setCreatedUser(user.getUsername());
        user.setCreatedTime(now);
        user.setModifiedUser(user.getUsername());
        user.setModifiedTime(now);

        // 表示用户名没有被占用，则允许注册
        // 调用持久层Integer insert(User user)方法，执行注册并获取返回值(受影响的行数)
        Integer rows = mapper.insert(user);
        // 判断受影响的行数是否不为1
        if (rows != 1) {
            // 是：插入数据时出现某种错误，则抛出InsertException异常
            throw new BusinessException("插入失败，请重新输入");

        }
    }

    /**
     * 登录
     * @param username
     * @param password
     * @return user对象
     */
    @Override
    public User login(String username, String password) {
        User loginAccount = mapper.SelectByName(username);
        if (loginAccount == null) {
            throw  new BusinessException("您输入的用户名不存在");
        }
        if (loginAccount.getIsDelete() == 1){
            throw new BusinessException("您输入的账户已被删除！");
        }
        String salt = loginAccount.getSalt();
        // 调用getMd5Password()方法，将参数password和salt结合起来进行加密
        String md5Password = getMd5Password(password, salt);
        // 判断查询结果中的密码，与以上加密得到的密码是否不一致
        if (!loginAccount.getPassword().equals(md5Password)) {
            // 是：抛出PasswordNotMatchException异常
            throw new BusinessException("密码验证失败的错误");
        }
        var user = new User();
        user.setUid(loginAccount.getUid());
        user.setAvatar(loginAccount.getAvatar());
        user.setUsername(loginAccount.getUsername());
        return user;
    }

    /**
     * TODO 修改密码
     * @param uid
     * @param username
     * @param oldPassword
     * @param newPassword
     */
    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
        //根据uid查询用户
        User user = mapper.findById(uid);
        if(user == null){
            throw new BusinessException("用户数据不存在");
        }
        if(user.getIsDelete() == 1){
            throw new BusinessException("该用户已经被删除");
        }
        String salt = user.getSalt();
        if(!getMd5Password(oldPassword,salt).contentEquals(user.getPassword())){
            throw new BusinessException("原密码错误");
        }
        String newMd5Password = getMd5Password(newPassword, salt);

        if(mapper.updatePasswordByUid(uid, newMd5Password, username, new Date()) != 1){
            throw new SystemException("修改时出现未知错误，请联系管理员处理！！");
        }
    }

    /**
     * TODO 获取当前登录的用户的信息
     * @param uid 当前登录的用户的id
     * @return 当前登录的用户的信息
     */
    @Override
    public User getById(Integer uid) {
        User user = mapper.findById(uid);
        if(user == null) {
            throw new BusinessException("用户未找到");
        }
        if(user.getIsDelete() == 1){
            throw new BusinessException("该用户已经被删除");
        }
        User data = new User();
        data.setPhone(user.getPhone());
        data.setGender(user.getGender());
        data.setUsername(user.getUsername());
        data.setEmail(user.getEmail());
        return data;
    }
    /**
     * TODO 修改用户资料
     * @param uid 当前登录的用户的id
     * @param username 当前登录的用户名
     * @param user 用户的新的数据
     */
    @Override
    public void changeInfo(Integer uid, String username, User user) {
        User res = mapper.findById(uid);
        if(res == null) {
            throw new BusinessException("用户未找到");
        }
        if(res.getIsDelete() == 1){
            throw new BusinessException("该用户已经被删除");
        }
        user.setModifiedTime(new Date());
        user.setModifiedUser(username);
        user.setUid(uid);

        if(mapper.updateUserByUid(user) != 1){
            throw new SystemException("更新用户数据时出现未知错误，请联系系统管理员");
        }
    }

    @Override
    public void changeAvatar(Integer uid, String username, String avatar) {
        User res = mapper.findById(uid);
        if(res == null){
            throw new BusinessException("该用户不存在");
        }
        if(res.getIsDelete() == 1){
            throw new BusinessException("该用户已被删除");
        }
        Date now = new Date();
        mapper.updateAvatarByUid(uid,avatar,username,now);
    }


    /**
     * 执行密码加密
     * @param password 原始密码
     * @param salt 盐值
     * @return 加密后的密文
     */
    private String getMd5Password(String password, String salt) {
        /*
         * 加密规则：
         * 1、无视原始密码的强度
         * 2、使用UUID作为盐值，在原始密码的左右两侧拼接
         * 3、循环加密3次
         */
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}

