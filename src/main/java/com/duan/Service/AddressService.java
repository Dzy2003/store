package com.duan.Service;

import com.duan.entity.Address;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface AddressService {
    /**
     * 创建新的收货地址
     * @param uid 当前登录的用户的id
     * @param username 当前登录的用户名
     * @param address 用户提交的收货地址数据
     */
    void addAddress(Address address,Integer uid, String username) throws UnsupportedEncodingException;

    List<Address> getAddressListByUid(Integer uid);

    /**
     * 设置默认收货地址
     * @param aid 收货地址id
     * @param uid 归属的用户id
     * @param username 当前登录的用户名
     */
    void setDefaultAddress(Integer uid, String username,Integer aid);

    void delete(Integer uid, String username,Integer aid);

    Address getAddressByAid(Integer aid,Integer uid);
}
