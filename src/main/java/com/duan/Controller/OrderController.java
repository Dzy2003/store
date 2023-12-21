package com.duan.Controller;

import com.duan.Service.OrderService;
import com.duan.entity.Order;
import com.duan.util.R;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {
    OrderService service;
    @PostMapping
    public R<Order> createOrder(@RequestParam("aid") Integer aid,@RequestParam("cids") Integer[] cids, HttpSession session){
        Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
        String username =  session.getAttribute("username").toString();
        Order order = service.createOrder(aid, cids, uid, username);
        return R.success(order);
    }


    @Autowired
    public OrderController(OrderService service) {
        this.service = service;
    }
}
