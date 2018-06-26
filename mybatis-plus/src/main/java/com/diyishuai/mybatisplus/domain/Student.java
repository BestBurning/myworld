package com.diyishuai.mybatisplus.domain;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.Version;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 */
public class Student extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Integer id;

	private String name;
	@TableField("is_admin")
	private Boolean isAdmin;

	@Version
	private Integer version;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getAdmin() {
		return isAdmin;
	}
	public void setAdmin(Boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

	protected Serializable pkVal() {
		return this.id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public String toString() {
		return "Student{" +
				"id=" + id +
				", name='" + name + '\'' +
				", isAdmin=" + isAdmin +
				", version=" + version +
				'}';
	}
}
