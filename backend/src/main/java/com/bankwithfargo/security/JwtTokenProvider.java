package com.bankwithfargo.security;

import java.security.Key;
import java.util.Date;

import com.bankwithfargo.exception.BankAppException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Component
public class JwtTokenProvider  {
	
	@Value("${app.jwt.secret}")
	private String jwtSecret;
	@Value("${app.jwt.expiration-milliseconds}")
	private long expiryTime;
	
	public String generateToken(String email) {

		Date currentDate=new Date();
		Date expiryDate=new Date(currentDate.getTime()+expiryTime);
		
		String token=Jwts.builder()
		.setSubject(email)
		.setIssuedAt(currentDate)
		.setExpiration(expiryDate)
		.signWith(key())
		.compact();
		
		return token;
		
	}
	
	public String getUsername(String token) {
		
	Claims claims=Jwts.parserBuilder()
		.setSigningKey(key())
		.build()
		.parseClaimsJws(token)
		.getBody();
		 return claims.getSubject();
	}
	
	private Key key() {
		return Keys.hmacShaKeyFor(jwtSecret.getBytes());
	}
	
	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
			.setSigningKey(key())
			.build()
			.parse(token);
			return true;
		}
		catch(MalformedJwtException ex) {
			throw new BankAppException(HttpStatus.BAD_REQUEST, "Invalid Token");
		}
		catch(ExpiredJwtException ex) {
			throw new BankAppException(HttpStatus.BAD_REQUEST, "Token Expired");
		}
		catch(UnsupportedJwtException ex) {
			throw new BankAppException(HttpStatus.BAD_REQUEST, "Token is not supported");
		}
		catch(IllegalArgumentException ex) {
			throw new BankAppException(HttpStatus.BAD_REQUEST, "Token Claims String is empty");
		}
	}

}
