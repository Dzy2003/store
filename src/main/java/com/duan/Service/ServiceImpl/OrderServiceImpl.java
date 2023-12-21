package com.duan.Service.ServiceImpl;

import com.duan.Controller.Exception.BusinessException;
import com.duan.Controller.Exception.SystemException;
import com.duan.Mapper.OrderMapper;
import com.duan.Service.AddressService;
import com.duan.Service.CartService;
import com.duan.Service.OrderService;
import com.duan.entity.Address;
import com.duan.entity.Order;
import com.duan.entity.OrderItem;
import com.duan.vo.CartVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
@Service
public class OrderServiceImpl implements OrderService {
    OrderMapper mapper;
    AddressService Addressservice;
    CartService cartService;
    @Autowired
    public OrderServiceImpl(OrderMapper mapper, AddressService addressservice, CartService cartService) {
        this.mapper = mapper;
        Addressservice = addressservice;
        this.cartService = cartService;
    }

    @Override
    public Order createOrder(Integer aid, Integer[] cids, Integer uid, String username) {
        //1.插入订单数据
        List<CartVo> voByCids = cartService.getVOByCids(uid, cids);
        long totalPrice = 0;
        for (CartVo voByCid : voByCids) {
            totalPrice += voByCid.getPrice() * voByCid.getNum();
        }
        Order order = new Order();
        order.setUid(uid);
        Address address = Addressservice.getAddressByAid(aid, uid);
        // 补全数据：收货地址相关的6项
        order.setRecvName(address.getName());
        order.setRecvPhone(address.getPhone());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());
        // 补全数据：totalPrice
        order.setTotalPrice(totalPrice);
        // 补全数据：status
        order.setStatus(0);
        // 补全数据：下单时间
        Date now = new Date();
        order.setOrderTime(now);
        // 补全数据：日志
        order.setCreatedUser(username);
        order.setCreatedTime(now);
        order.setModifiedUser(username);
        order.setModifiedTime(now);


        //2.插入订单商品数据
        if(mapper.insertOrder(order) != 1) throw new SystemException("创建订单异常");
        for (CartVo cart : voByCids) {
            // 创建订单商品数据
            OrderItem item = new OrderItem();
            // 补全数据：setOid(order.getOid())
            item.setOid(order.getOid());
            // 补全数据：pid, title, image, price, num
            item.setPid(cart.getPid());
            item.setTitle(cart.getTitle());
            item.setImage(cart.getImage());
            item.setPrice(cart.getRealPrice());
            item.setNum(cart.getNum());
            // 补全数据：4项日志
            item.setCreatedUser(username);
            item.setCreatedTime(now);
            item.setModifiedUser(username);
            item.setModifiedTime(now);
            // 插入订单商品数据
            if (mapper.insertOrderItem(item) != 1) throw new SystemException("插入订单商品数据时出现未知错误，请联系系统管理员");
        }
        return order;
    }
}
