package com.usermanagemet.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usermanagemet.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	  Optional<User> findByFirstName(String username);

	  Boolean existsByFirstName(String username);

	  Boolean existsByEmail(String email);

}
