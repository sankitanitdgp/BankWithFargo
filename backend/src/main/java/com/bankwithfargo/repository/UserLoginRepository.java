package com.bankwithfargo.repository;

import com.bankwithfargo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginRepository extends JpaRepository<User, Long>{
    User findOneByEmail(String email);
}