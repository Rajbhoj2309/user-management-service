package com.usermanagemet.utils;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.usermanagemet.security.services.UserDetailsImpl;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtilService {

	private static final Logger logger = LoggerFactory.getLogger(JwtUtilService.class);
	
	@Value("${allinone.app.SECRETE_KEY}")
	private String SECRET_KEY ;
	
	 @Value("${allinone.app.keyExpirationMs}")
	 private int keyExpirationMs;
	 
	  public String generateToken(Authentication authentication) {

		    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();

		    return Jwts.builder()
		        .setSubject((userPrincipal.getUsername()))
		        .setIssuedAt(new Date())
//		        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
		        .setExpiration(new Date((new Date()).getTime() + keyExpirationMs))
		        .signWith(key(), SignatureAlgorithm.HS256)
		        .compact();
		  }
//		//public String generateToken(UserDetails userDetails) {
//		Map<String, Object> claims = new HashMap<>();
//		claims.put("role",
//				userDetails.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority).orElse("USER"));
//
//		return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername())
//				.setIssuedAt(new Date(System.currentTimeMillis()))
//				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 hour
//				.signWith(Keys.hmacShaKeyFor(SECRET.getBytes()), SignatureAlgorithm.HS256).compact();
//	}
		  private Key key() {
		    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY));
		  }

		  public String extractUserNameFromJwtToken(String token) {
		    return Jwts.parserBuilder().setSigningKey(key()).build()
		               .parseClaimsJws(token).getBody().getSubject();
		  }

		  public boolean isTokenValid(String authToken) {
		    try {
		      Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
		      return true;
		    } catch (MalformedJwtException e) {
		      logger.error("Invalid JWT token: {}", e.getMessage());
		    } catch (ExpiredJwtException e) {
		      logger.error("JWT token is expired: {}", e.getMessage());
		    } catch (UnsupportedJwtException e) {
		      logger.error("JWT token is unsupported: {}", e.getMessage());
		    } catch (IllegalArgumentException e) {
		      logger.error("JWT claims string is empty: {}", e.getMessage());
		    }

		    return false;
		  }
//	public boolean isTokenValid(String token, UserDetails userDetails) {
//		return extractUsername(token).equals(userDetails.getUsername());
//	}



}
