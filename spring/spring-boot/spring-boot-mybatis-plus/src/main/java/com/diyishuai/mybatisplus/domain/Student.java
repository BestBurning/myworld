package com.diyishuai.mybatisplus.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.ToString;


/**
 * <p>
 * 
 * </p>
 *
 */
@Data
@ToString
public class Student  {

	@TableId(value = "id",type = IdType.AUTO)
    private Integer id;
	private String name;
	@TableField("is_admin")
	private boolean isAdmin;
	private Integer version;

}
