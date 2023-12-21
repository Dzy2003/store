package com.duan.Mapper;

import com.duan.entity.District;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface DistrictMapper {
    /**
     * 根据父级地区代码查询下属于父级地区的所有地区，根据地区的code排序
     * @param parent 父级地址行政代码
     * @return 下属于父级地区的所有地区
     */
    @Select("SELECT * FROM t_dict_district WHERE parent = #{parent} ORDER BY code ASC")
    List<District> selectByParent(@Param("parent") String parent);

    /**
     * 根据code行政代号查询名称
     * @param code 城市的行政代号
     * @return 城市名称
     */
    @Select("SELECT name FROM t_dict_district WHERE code = #{code}")
    String selectNameByCode(@Param("code") String code);
}
