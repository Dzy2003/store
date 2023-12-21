package com.duan.Service;

import com.duan.entity.District;

import java.util.List;

public interface DistrictService {
    List<District> getByParent(String parent);

    /**
     * 通过编号获取行政单位的名称
     * @param code
     * @return
     */
    String GetNameByCode(String code);
}
