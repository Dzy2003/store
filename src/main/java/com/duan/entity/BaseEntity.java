package com.duan.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
@Data
abstract class BaseEntity implements Serializable {
    private String createdUser;
    private Date createdTime;
    private String modifiedUser;
    private Date modifiedTime;
}
