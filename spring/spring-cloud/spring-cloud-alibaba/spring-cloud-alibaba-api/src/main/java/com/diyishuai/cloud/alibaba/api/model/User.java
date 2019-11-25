package com.diyishuai.cloud.alibaba.api.model;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author: Bruce
 * @date: 2019-11-22
 * @description:
 */
@Data
@Accessors(chain = true)
public class User implements Serializable {

    private Long id;
    private String name;
    private Integer age;
    private String email;

}
