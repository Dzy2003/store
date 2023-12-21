package com.duan.Service;

import com.duan.entity.Cart;
import com.duan.vo.CartVo;

import java.util.List;

public interface CartService {
    /**
     * 添加购物车
     * @param uid 当前登录的用户的id
     * @param pid 添加购物车的商品id
     * @param amount 添加商品的数量
     * @param username 当前登录的用户的用户名
     */
    void AddCart(Integer uid, Integer pid,Integer amount, String username);

    /**
     * 获取该用户购物车列表
     * @param uid 当前登录的用户的id
     * @return 该用户的购物车列表
     */
    List<CartVo> findCartByUid(Integer uid);
    /**
     * 将购物车中某商品的数量加1
     * @param cid 购物车数量的id
     * @param uid 当前登录的用户的id
     * @param username 当前登录的用户名
     * @return 增加成功后新的数量
     */
    Integer AddCartNum(Integer cid,Integer uid, String username);

    /**
     * 根据若干个购物车数据id查询详情的列表
     * @param uid 当前登录的用户的id
     * @param cids 若干个购物车数据id
     * @return 匹配的购物车数据详情的列表
     */
    List<CartVo> getVOByCids(Integer uid, Integer[] cids);
}
