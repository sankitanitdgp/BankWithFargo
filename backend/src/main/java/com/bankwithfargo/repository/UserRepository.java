package com.bankwithfargo.repository;

import com.bankwithfargo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    User findOneByEmail(String email);
}