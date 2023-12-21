package com.duan;

import com.duan.Mapper.ProductMapper;
import com.duan.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ProductMapperTests {
        @Autowired
        private ProductMapper productMapper;

        @Test
        public void findHotList() {
            List<Product> list = productMapper.findHotProducts();
            System.out.println("count=" + list.size());
            for (Product item : list) {
                System.out.println(item);
            }
        }
}
