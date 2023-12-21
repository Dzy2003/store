package com.duan.Service;

import com.duan.Mapper.ProductMapper;
import com.duan.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findHotProducts();

    Product findById(Integer id);
}
