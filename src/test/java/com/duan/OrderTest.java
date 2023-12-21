package com.duan;

import com.duan.Mapper.OrderMapper;
import com.duan.Service.OrderService;
import com.duan.entity.Order;
import com.duan.entity.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OrderTest {
    @Autowired
    private OrderMapper mapper;
    @Autowired
    private OrderService orderService;
    @Test
    public void insertOrder() {
        Order order = new Order();
        order.setUid(31);
        order.setRecvName("小王");
        Integer rows = mapper.insertOrder(order);
        System.out.println("rows=" + rows);
    }
    @Test
    public void insertOrderItem() {
        OrderItem orderItem = new OrderItem();
        orderItem.setOid(1);
        orderItem.setPid(2);
        orderItem.setTitle("高档铅笔");
        Integer rows = mapper.insertOrderItem(orderItem);
        System.out.println("rows=" + rows);
    }
    @Test
    public void create() {

            Integer aid = 2;
            Integer[] cids = { 5, 6};
            Integer uid = 7;
            String username = "订单管理员";
            Order order = orderService.createOrder(aid, cids, uid, username);
            System.out.println(order);

    }
}
