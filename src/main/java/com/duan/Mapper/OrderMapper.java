package com.duan.Mapper;

import com.duan.entity.Order;
import com.duan.entity.OrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;

@Mapper
public interface OrderMapper {
    /**
     * 插入订单数据
     * @param order 订单数据
     * @return 受影响的行数
     */
    @Insert("INSERT INTO t_order (uid, recv_name, recv_phone, recv_province, recv_city, recv_area, recv_address, total_price, status, order_time, pay_time, created_user, created_time, modified_user, modified_time) VALUES " +
            "(#{uid}, #{recvName}, #{recvPhone}, #{recvProvince}, #{recvCity}, #{recvArea},\n" +
            "            #{recvAddress}, #{totalPrice}, #{status}, #{orderTime}, #{payTime}, #{createdUser},\n" +
            "            #{createdTime}, #{modifiedUser}, #{modifiedTime})")
    @Options(useGeneratedKeys = true,keyProperty = "oid",keyColumn = "oid")
    Integer insertOrder(Order order);
    /**
     * 插入订单商品数据
     * @param orderItem 订单商品数据
     * @return 受影响的行数
     */
    @Insert("INSERT INTO t_order_item (oid, pid, title, image, price, num, created_user, created_time, modified_user, modified_time) values " +
            "( #{oid}, #{pid}, #{title}, #{image}, #{price}, #{num}, #{createdUser},\n" +
            "            #{createdTime}, #{modifiedUser}, #{modifiedTime})")
    Integer insertOrderItem(OrderItem orderItem);
}
