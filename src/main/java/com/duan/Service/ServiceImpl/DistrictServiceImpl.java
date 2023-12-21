package com.duan.Service.ServiceImpl;

import com.duan.Mapper.DistrictMapper;
import com.duan.Service.DistrictService;
import com.duan.entity.District;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class DistrictServiceImpl implements DistrictService {
    @Autowired
    DistrictMapper mapper ;
    @Override
    public List<District> getByParent(String parent) {
        List<District> list = mapper.selectByParent(parent);
        for (District district : list) {
            district.setId(null);
            district.setParent(null);
        }
        return list;
    }

    @Override
    public String GetNameByCode(String code) {
        return mapper.selectNameByCode(code);
    }
}
