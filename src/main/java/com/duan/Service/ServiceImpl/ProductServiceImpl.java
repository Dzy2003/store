package com.duan.Service.ServiceImpl;

import com.duan.Controller.Exception.BusinessException;
import com.duan.Mapper.ProductMapper;
import com.duan.Service.ProductService;
import com.duan.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductServiceImpl implements ProductService {
    ProductMapper mapper;
    @Override
    public List<Product> findHotProducts() {
        List<Product> hotProducts = mapper.findHotProducts();
        for (Product product : hotProducts) {
            product.setPriority(null);
            product.setCreatedUser(null);
            product.setCreatedTime(null);
            product.setModifiedUser(null);
            product.setModifiedTime(null);
        }
        return hotProducts;
    }

    @Override
    public Product findById(Integer id) {
        Product product = mapper.selectById(id);
        if(product == null) throw new BusinessException("该商品已不存在");
        //将前端不需要的属性置空
        product.setPriority(null);
        product.setCreatedUser(null);
        product.setCreatedTime(null);
        product.setModifiedUser(null);
        product.setModifiedTime(null);
        // 返回查询结果
        return product;
    }


    @Autowired
    public void setMapper(ProductMapper mapper) {
        this.mapper = mapper;
    }
}
