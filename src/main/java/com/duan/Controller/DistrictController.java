package com.duan.Controller;

import com.duan.Service.DistrictService;
import com.duan.entity.District;
import com.duan.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/districts")
public class DistrictController {
    @Autowired
    DistrictService service;

    /**
     * 通过前端传入的parent查询下属地区
     * @param parent 父级编号(路径传参)
     * @return 所有下属的地区列表
     */
    @GetMapping("/{parent}")
    public R<List<District>> getByDistrict(@PathVariable("parent") String parent){
        List<District> list = service.getByParent(parent);
        return R.success(list);
    }
}
