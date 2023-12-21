package com.duan;

import com.duan.Mapper.AddressMapper;
import com.duan.Mapper.DistrictMapper;
import com.duan.Mapper.UserMapper;
import com.duan.Service.AddressService;
import com.duan.Service.UserService;
import com.duan.entity.Address;
import com.duan.entity.District;
import com.duan.entity.User;
import jakarta.annotation.Resource;
import jakarta.annotation.Resources;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

@SpringBootTest
class StoreApplicationTests {
    @Resource
    private DataSource dataSource;
    @Autowired
    UserMapper mapper;
    @Autowired
    UserService userService;
    @Autowired
    AddressMapper mapper1;
    @Autowired
    AddressService addressService;

    @Test
    void contextLoads() {

    }

    @Test
    void getConnection() throws Exception {
        System.out.println(dataSource.getConnection());
    }

    @Test
    void TestInsert() {
        User user = new User();
        user.setUsername("user02");
        user.setPassword("123456");
        user.setIsDelete(1);
        userService.reg(user);

    }

    @Test
    void TestSelect() {
        User user = mapper.SelectByName("user01");
        System.out.println(user);

    }

    @Test
    public void login() {

        User login = userService.login("lower1234", "123456");
        System.out.println(login.getAvatar() + login.getUsername() + login.getUid());
        System.out.println(login.getIsDelete());

    }

    @Test
    void testUpdate() {
        Integer uid = 9;
        String username = "user02";
        String oldPassword = "123456";
        String newPassword = "888888";
        userService.changePassword(uid, username, oldPassword, newPassword);
    }

    @Test
    public void updateInfoByUid() {
        User user = new User();
        user.setUid(8);
        user.setPhone("17858802222");
        user.setEmail("admin@cy.com");
        user.setGender(1);
        user.setModifiedUser("系统管理员");
        user.setModifiedTime(new Date());
        Integer rows = mapper.updateUserByUid(user);
        System.out.println("rows=" + rows);
    }

    @Test
    public void getByUid() {
        try {
            Integer uid = 4;
            User user = userService.getById(uid);
            System.out.println(user);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void changeInfo() {
        try {
            Integer uid = 4;
            String username = "数据管理员";
            User user = new User();
            user.setPhone("15512328888");
            user.setEmail("admin03@cy.cn");
            user.setGender(2);
            userService.changeInfo(uid, username, user);
            System.out.println("OK.");
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    //测试上传头像
    @Test
    public void updateAvatarByUid() {
        try {
            Integer uid = 9;
            String username = "头像管理员";
            String avatar = "/upload/avatar.png";
            userService.changeAvatar(uid, username, avatar);
            System.out.println("OK.");
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void insert() {
        Address address = new Address();
        address.setUid(18);
        address.setName("admin");
        address.setPhone("17858802974");
        address.setAddress("雁塔区小寨赛格");
        Integer rows = mapper1.insert(address);
        System.out.println("rows=" + rows);
    }

    @Test
    public void addNewAddress() {
        try {
            Integer uid = 18;
            String username = "管理员";
            Address address = new Address();
            address.setName("张三");
            address.setPhone("17858805555");
            address.setAddress("雁塔区小寨华旗");
            addressService.addAddress(address, uid, username);
            System.out.println("OK.");
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
    @Autowired
    private DistrictMapper districtMapper;

    @Test
    public void findByParent() {
        String parent = "110100";
        List<District> list = districtMapper.selectByParent(parent);
        System.out.println("count=" + list.size());
        for (District district : list) {
            System.out.println(district);
        }
    }
    @Test
    public void updateNonDefaultByUid() {
        Integer uid = 7;
        Integer rows = mapper1.updateNonDefaultByUid(uid);
        System.out.println("rows=" + rows);
    }

    @Test
    public void updateDefaultByAid() {
        Integer aid = 8;
        String modifiedUser = "管理员";
        Date modifiedTime = new Date();
        Integer rows = mapper1.updateDefaultByAid(aid, modifiedUser, modifiedTime);
        System.out.println("rows=" + rows);
    }

    @Test
    public void findByAid() {
        Integer aid = 12;
        Address result = mapper1.selectAddressByAid(aid);
        System.out.println(result);
    }
    @Test
    public void findNameByCode() {
        System.out.println(districtMapper.selectNameByCode("110101"));
    }
    @Test
    public void setDefault() {
        try {
            Integer aid = 12;
            Integer uid = 7;
            String username = "系统管理员";
            addressService.setDefaultAddress(uid, username, aid);
            System.out.println("OK.");
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void deleteByAid() {
        Integer aid = 1;
        Integer rows = mapper1.deleteAddressByAid(aid);
        System.out.println("rows=" + rows);
    }

    @Test
    public void findLastModified() {
        Integer uid = 7;
        Integer aid = mapper1.findLastModified(uid);
        System.out.println(aid);
    }
    @Test
    public void delete() {
        try {
            Integer aid = 13;
            Integer uid = 7;
            String username = "lower123";
            addressService.delete(uid,username ,aid);
            System.out.println("OK.");
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

}

