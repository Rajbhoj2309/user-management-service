package com.usermanagemet.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usermanagemet.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

}
