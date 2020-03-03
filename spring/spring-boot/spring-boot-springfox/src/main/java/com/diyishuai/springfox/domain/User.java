package com.diyishuai.springfox.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author: Bruce
 * @date: 2020/2/22
 * @description:
 */
@Data
@ApiModel("用户实体")
@Accessors(chain = true)
public class User {

    @ApiModelProperty("用户id")
    private Long id;
    @ApiModelProperty("用户名称")
    private String name;
    @ApiModelProperty("用户年龄")
    private Integer age;

}
