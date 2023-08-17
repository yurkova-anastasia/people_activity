package com.ku.users.repository;

import com.ku.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("FROM User u LEFT JOIN FETCH u.roles r LEFT JOIN FETCH r.authorities WHERE u.username=:username")
    User findByUsername(String username);
}
