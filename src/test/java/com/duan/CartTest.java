package com.duan;

import com.duan.Mapper.CartMapper;
import com.duan.Service.CartService;
import com.duan.entity.Cart;
import com.duan.vo.CartVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class CartTest {
    @Autowired
    private CartMapper cartMapper;
    @Autowired
    CartService cartService;
    @Test
    public void insert() {
        Cart cart = new Cart();
        cart.setUid(1);
        cart.setPid(2);
        cart.setNum(3);
        cart.setPrice(4L);
        Integer rows = cartMapper.insert(cart);
        System.out.println("rows=" + rows);
    }

    @Test
    public void updateNumByCid() {
        Integer cid = 1;
        Integer num = 10;
        String modifiedUser = "购物车管理员";
        Date modifiedTime = new Date();
        Integer rows = cartMapper.UpdateNumByCid(cid, num, modifiedUser, modifiedTime);
        System.out.println("rows=" + rows);
    }

    @Test
    public void findByUidAndPid() {
        Integer uid = 2;
        Integer pid = 10000007;
        Cart result = cartMapper.selectCartById(pid, uid);
        System.out.println(result);
    }
    @Test
    public void addToCart() {
        try {
            Integer uid = 2;
            Integer pid = 10000007;
            Integer amount = 1;
            String username = "Tom";
            cartService.AddCart(uid, pid, amount, username);
            System.out.println("OK.");
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void findVOByUid() {
        List<CartVo> list = cartMapper.selectVOByUid(7);
        System.out.println(list);
    }
    @Test
    public void addNum() {
        try {
            Integer cid = 6;
            Integer uid = 7;
            String username = "管理员";
            Integer num = cartService.AddCartNum(cid, uid, username);
            System.out.println("OK. New num=" + num);
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }
    @Test
    public void findVOByCids() {
        Integer[] cids = {4,5,6};
        List<CartVo> list = cartMapper.selectVoByCids(cids);
        System.out.println("count=" + list.size());
        for (CartVo item : list) {
            System.out.println(item);
        }
    }

}

