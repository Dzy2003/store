package com.duan.Service.ServiceImpl;

import com.duan.Controller.Exception.BusinessException;
import com.duan.Mapper.CartMapper;
import com.duan.Service.CartService;
import com.duan.Service.ProductService;
import com.duan.entity.Cart;
import com.duan.vo.CartVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    ProductService productService;
    CartMapper mapper;
    @Override
    public void AddCart(Integer uid, Integer pid, Integer amount, String username) {
        Cart res = mapper.selectCartById(pid, uid);
        //该用户未将该商品添加购物车
        if(res == null) {
            Cart cart = new Cart();
            cart.setNum(amount);
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setModifiedUser(username);
            cart.setCreatedTime(new Date());
            cart.setPrice(productService.findById(pid).getPrice());
            if(mapper.insert(cart) != 1) throw new BusinessException("插入商品数据时出现未知错误，请联系系统管理员");
            //该用户的购物车存在该商品，只需修改数量即可
        }else{
            if(mapper.UpdateNumByCid(res.getCid(),res.getNum()+amount,username,new Date()) != 1){
                throw new BusinessException("修改商品数量时出现未知错误，请联系系统管理员");
            }
        }

    }

    @Override
    public List<CartVo> findCartByUid(Integer uid) {
        return mapper.selectVOByUid(uid);
    }

    @Override
    public Integer AddCartNum(Integer cid,Integer uid, String username) {
        Cart cart = mapper.selectCartByCid(cid);
        if(cart == null) throw new BusinessException("该购物车数据不存在");
        if(cart.getUid() != uid) throw new BusinessException("非法访问");
        Integer nums = cart.getNum() + 1;
        if(mapper.UpdateNumByCid(cid,nums,username,new Date()) != 1) throw new BusinessException("更新数据时出现错误");
        return nums;
    }

    @Override
    public List<CartVo> getVOByCids(Integer uid, Integer[] cids) {
        List<CartVo> cartVos = mapper.selectVoByCids(cids);
        Iterator<CartVo> cartVoIterator = cartVos.iterator();
        while (cartVoIterator.hasNext()) {
            if(!cartVoIterator.next().getUid().equals(uid)) cartVoIterator.remove();
        }
        return cartVos;
    }


    @Autowired
    public CartServiceImpl(ProductService productService, CartMapper mapper) {
        this.productService = productService;
        this.mapper = mapper;
    }
}
