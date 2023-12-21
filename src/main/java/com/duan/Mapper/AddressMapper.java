package com.duan.Mapper;

import com.duan.entity.Address;
import com.duan.entity.District;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface AddressMapper {
    /**
     * 插入新的收货地址
     * @param address 要插入的地址数据
     * @return
     */
    @Insert("INSERT INTO t_address (uid, name, province_name, province_code, city_name, city_code, area_name, area_code, zip, address, phone, tel, tag, is_default, created_user, created_time, modified_user, modified_time) VALUES" +
            "( #{uid}, #{name}, #{provinceName}, #{provinceCode}, #{cityName}, #{cityCode}, #{areaName},\n" +
            "        #{areaCode}, #{zip}, #{address}, #{phone}, #{tel}, #{tag}, #{isDefault}, #{createdUser},\n" +
            "        #{createdTime}, #{modifiedUser}, #{modifiedTime})")
    Integer insert(Address address);

    /**
     * 根据uid查询收货地址个数
     * @param uid
     * @return 收货地址个数
     */
    @Select("SELECT count(*) FROM t_address where uid = #{uid}")
    Integer accountAddress(@Param("uid") Integer uid);

    /**
     * 根据uid查询用户收货地址
     * @param uid
     * @return 返回收货地址集合
     */
    @Select(("SELECT * FROM t_address WHERE uid = #{uid} ORDER BY is_default desc, created_time desc;"))
    List<Address> selectAddressListByUid(@Param("uid") Integer uid);

    /**
     * 将用户原本的默认地址设置为非默认
     * @param uid
     * @return
     */
    @Update("UPDATE t_address SET is_default = 0 WHERE uid = #{uid} AND is_default = 1")
    Integer updateNonDefaultByUid(Integer uid);

    /**
     * 将指定的收货地址设置为默认地址
     * @param aid 收货地址id
     * @param modifiedUser 修改执行人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    @Update("update t_address set is_default = 1,modified_user=#{modifiedUser},modified_time=#{modifiedTime} where aid = #{aid}")
    Integer updateDefaultByAid(@Param("aid") Integer aid,
                               @Param("modifiedUser") String modifiedUser,
                               @Param("modifiedTime") Date modifiedTime);
    /**
     * 根据收货地址aid值，查询收货地址详情
     * @param aid 收货地址id
     * @return 匹配的收货地址详情，如果没有匹配的数据，则返回null
     */
    @Select("select * from t_address where aid = #{aid} ")
    Address selectAddressByAid(@Param("aid") Integer aid);

    /**
     * 根据收货地址aid值，删除收货地址详情
     * @param aid 收货地址id
     * @return 删除成功返回1，失败返回0
     */
    @Delete("DELETE FROM t_address WHERE aid = #{aid}")
    Integer deleteAddressByAid(@Param("aid") Integer aid);

    /**
     * 查询某用户最后修改的收货地址aid
     * @param uid 归属的用户id
     * @return 该用户最后修改收货地址aid
     */
    @Select("SELECT aid FROM t_address where uid = #{uid} Order BY modified_time DESC LIMIT 0,1")
    Integer findLastModified(@Param("uid")Integer uid);

}
