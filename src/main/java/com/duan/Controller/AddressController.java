package com.duan.Controller;

import com.duan.Service.AddressService;
import com.duan.entity.Address;
import com.duan.util.R;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@RequestMapping("/addresses")
public class AddressController {
    private AddressService addressService;

    /**
     * 添加收货地址
     * @param address 前端表单传递数据
     * @param session
     * @return null
     */
    @PostMapping()
    public R<Void> addNewAddress(@RequestBody Address address, HttpSession session) {
        Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
        String username =  session.getAttribute("username").toString();
        try {
            addressService.addAddress(address, uid, username);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return R.success(null);
    }
    @GetMapping()
    public R<List<Address>> getAddressList(HttpSession session) {
        Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
        return R.success(addressService.getAddressListByUid(uid));
    }
    @PutMapping("/{aid}")
    public R<Void> setDefault(@PathVariable("aid") Integer aid, HttpSession session){
        Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
        String username =  session.getAttribute("username").toString();
        addressService.setDefaultAddress(uid,username,aid);
        return  R.success(null);
    }

    @DeleteMapping("/{aid}")
    public R<Void> deleteAddress(@PathVariable("aid") Integer aid,HttpSession session){
        Integer uid = Integer.valueOf(session.getAttribute("uid").toString());
        String username =  session.getAttribute("username").toString();
        addressService.delete(uid,username,aid);
        return R.success(null);
    }


    @Autowired
    public void setAddressService(AddressService addressService) {
        this.addressService = addressService;
    }
}
