package com.duan.Service.ServiceImpl;

import com.duan.Controller.Exception.BusinessException;
import com.duan.Controller.Exception.SystemException;
import com.duan.Mapper.AddressMapper;
import com.duan.Service.AddressService;
import com.duan.Service.DistrictService;
import com.duan.entity.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    private AddressMapper mapper;
    private int maxCount;
    private DistrictService districtService;



    @Override
    public void addAddress(Address address,Integer uid, String username) throws UnsupportedEncodingException {
        Integer count = mapper.accountAddress(uid);
        if(count == maxCount){
            throw new BusinessException("\"收货地址数量已经达到上限(\" + maxCount + \")！\"");
        }
        address.setName(URLDecoder.decode(address.getName(),"UTF-8"));//将中文url解码，可能会抛出UnsupportedEncodingException异常);
        address.setTag(URLDecoder.decode(address.getTag(),"UTF-8"));
        address.setAddress(URLDecoder.decode(address.getAddress(),"UTF-8"));
        address.setIsDefault(count == 0 ? 1 : 0);
        String provinceName = districtService.GetNameByCode(address.getProvinceCode());
        String cityName = districtService.GetNameByCode(address.getCityCode());
        String areaName = districtService.GetNameByCode(address.getCityCode());
        Date now = new Date();
        //封装address对象
        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);
        address.setUid(uid);
        address.setCreatedUser(username);
        address.setCreatedTime(now);
        address.setModifiedUser(username);
        address.setModifiedTime(now);

        if(mapper.insert(address) != 1){
            throw new SystemException("添加收货地址异常");
        }
    }

    @Override
    public List<Address> getAddressListByUid(Integer uid) {
        List<Address> addressList = mapper.selectAddressListByUid(uid);
        //将前端不需要显示的数据置空
        for (Address address : addressList) {
            address.setUid(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setCreatedUser(null);
            address.setCreatedTime(null);
            address.setModifiedUser(null);
            address.setModifiedTime(null);
        }
        return  addressList;
    }

    @Override
    @Transactional
    public void setDefaultAddress(Integer uid, String username, Integer aid) {
        //获取到根据aid获得需要设置对象的aid
        Address defaultAddress = mapper.selectAddressByAid(aid);
        if(defaultAddress == null) throw new BusinessException("尝试访问的收货地址数据不存在");
        if(!uid.equals(defaultAddress.getUid())) throw new BusinessException("非法访问的异常");
        if(mapper.updateNonDefaultByUid(uid) != 1) throw new BusinessException("设置默认收货地址时出现未知错误[1]");
        if(mapper.updateDefaultByAid(aid,username,new Date()) != 1) throw new BusinessException("设置默认收货地址时出现未知错误[2]");
    }

    @Override
    public void delete(Integer uid, String username, Integer aid) {
        Address deleteAddress = mapper.selectAddressByAid(aid);
        if(deleteAddress == null) throw new BusinessException("收货地址数据已被删除");
        if(!uid.equals(deleteAddress.getUid())) throw new BusinessException("非法访问");
        if(mapper.deleteAddressByAid(aid) != 1) throw new SystemException("删除异常");
        if(mapper.accountAddress(uid) == 0 || deleteAddress.getIsDefault() == 0) return;
        //若删除的收货地址为默认地址则更新默认地址为最新修改的地址
        if(mapper.updateDefaultByAid(mapper.findLastModified(uid),username,new Date()) != 1)
            throw new BusinessException("更新收货地址数据时出现未知错误，请联系系统管理员");
    }

    @Override
    public Address getAddressByAid(Integer aid ,Integer uid) {
        Address address = mapper.selectAddressByAid(aid);
        if(address == null) throw new BusinessException("尝试访问的收货地址数据不存在");
        if(address.getUid() != uid) throw new BusinessException("非法访问");
        //不需要的数据置空
        address.setProvinceCode(null);
        address.setCityCode(null);
        address.setAreaCode(null);
        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);
        return address;
    }


    @Autowired
    public void setDistrictService(DistrictService districtService) {
        this.districtService = districtService;
    }
    @Autowired
    public void setMapper(AddressMapper mapper) {
        this.mapper = mapper;
    }
    @Value("${user.address.max-count}")
    public void setMaxCount(int maxCount) {
        this.maxCount = maxCount;
    }
}
