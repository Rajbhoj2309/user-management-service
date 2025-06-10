package com.usermanagemet.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.usermanagemet.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{

	void save(String jwtToken);
	
	Optional<RefreshToken> findByUserId(Long userId);
	
	void deleteByUserId(Long userId);

}
