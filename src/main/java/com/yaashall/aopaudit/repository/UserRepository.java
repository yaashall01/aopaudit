package com.yaashall.aopaudit.repository;

import com.yaashall.aopaudit.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Yassine CHALH
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
