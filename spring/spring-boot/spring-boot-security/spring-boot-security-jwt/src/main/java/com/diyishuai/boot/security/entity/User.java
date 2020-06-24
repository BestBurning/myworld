package com.diyishuai.boot.security.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * @author: Bruce
 * @date: 2020/5/6
 * @description:
 */
@Entity
@Data
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

}
