package com.usermanagemet.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usermanagemet.domain.Role;
import com.usermanagemet.enums.RoleEnum;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	
	Optional<Role> findByName(String name);
	
	List<Role>findAll();
	
	boolean existsByName(String name);
}
