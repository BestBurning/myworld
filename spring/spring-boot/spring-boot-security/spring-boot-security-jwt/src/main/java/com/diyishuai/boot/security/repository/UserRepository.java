package com.diyishuai.boot.security.repository;

import com.diyishuai.boot.security.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author: Bruce
 * @date: 2020/5/6
 * @description:
 */
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByUsername(String username);

}
