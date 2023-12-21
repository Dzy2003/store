package com.duan.Mapper;

import com.duan.entity.Cart;
import com.duan.vo.CartVo;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface CartMapper {
    /**
     * 插入购物车数据
     * @param cart 购物车数据
     * @return 受影响的行数
     */
    @Insert("INSERT INTO t_cart(uid, pid, price, num, created_user, created_time, modified_user, modified_time)" +
            "values (#{uid}, #{pid}, #{price}, #{num}, #{createdUser}, #{createdTime}, #{modifiedUser}, #{modifiedTime})")
    Integer insert(Cart cart);
    /**
     * 修改购物车数据中商品的数量
     * @param cid 购物车数据的id
     * @param num 新的数量
     * @param modifiedUser 修改执行人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    @Update("UPDATE t_cart SET num = #{num}, modified_user = #{modifiedUser}, modified_time = #{modifiedTime} where cid = #{cid}")
    Integer UpdateNumByCid(@Param("cid") Integer cid,
                           @Param("num") Integer num,
                           @Param("modifiedUser") String modifiedUser,
                           @Param("modifiedTime") Date modifiedTime);
    /**
     * 根据用户id和商品id查询购物车中的数据
     * @param uid 用户id
     * @param pid 商品id
     * @return 匹配的购物车数据，如果该用户的购物车中并没有该商品，则返回null
     */
    @Select("select * from t_cart where uid = #{uid} and pid = #{pid}")
    Cart selectCartById(@Param("pid") Integer pid,@Param("uid") Integer uid);

    /**
     * 查询某用户的购物车数据
     * @param uid 用户id
     * @return 该用户的购物车数据的列表
     */
    @Select("select cid,pid,uid,c.price,c.num,p.title,p.price as realPrice,p.image " +
            "from t_product p , t_cart c " +
            "where p.id = c.pid and c.uid = #{uid} "+
            "ORDER BY c.created_time DESC")
    List<CartVo> selectVOByUid(@Param("uid") Integer uid);

    /**
     * 根据购物车数据id查询购物车数据详情
     * @param cid 购物车数据id
     * @return 匹配的购物车数据详情，如果没有匹配的数据则返回null
     */
    @Select("select * from t_cart where cid = #{cid}")
    Cart selectCartByCid(@Param("cid") Integer cid);

    List<CartVo> selectVoByCids(Integer[] cids);
}
