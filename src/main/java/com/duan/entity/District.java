package com.duan.entity;

import lombok.Data;

@Data
/**
 *  省/市/区数据的实体类
 */
public class District {
    private Integer id;
    private String parent;
    private String code;
    private String name;
}
