package com.duan.Controller;

import com.duan.Service.ProductService;
import com.duan.entity.Product;
import com.duan.util.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService service;
    @GetMapping
    public R<List<Product>> getHotProducts() {
        return R.success(service.findHotProducts());
    }

    @GetMapping({"/{id}"})
    public R<Product> getProduct(@PathVariable("id") Integer id){
        return R.success(service.findById(id));
    }




    @Autowired
    public void setService(ProductService service) {
        this.service = service;
    }
}
