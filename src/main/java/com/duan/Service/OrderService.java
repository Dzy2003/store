package com.duan.Service;

import com.duan.entity.Order;

public interface OrderService {
    public Order createOrder(Integer aid, Integer[] cids, Integer uid, String username);
}
