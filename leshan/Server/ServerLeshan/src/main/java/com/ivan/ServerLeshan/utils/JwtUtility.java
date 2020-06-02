package com.ivan.ServerLeshan.utils;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;

import com.ivan.ServerLeshan.bean.Credentials;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

public class JwtUtility {

	public static final String SECRET = "secret_secret_secret_secret_secret";
	public static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET.getBytes(StandardCharsets.UTF_8));
	public static final int MINUTES = 30;
	public static final String PREFIX = "Bearer ";
	
	public static String generateToken(Credentials credentials) {
		Instant now = Instant.now();
		
		JwtBuilder builder = Jwts.builder()
				.setIssuer("ERTIS")
				.setSubject("Blackbox API")
				.claim("username", credentials.getUsername())
				.claim("email", credentials.getEmail())
				.claim("role", credentials.getRole())
				.setIssuedAt(Date.from(now))
				.setExpiration(Date.from(now.plus(MINUTES, ChronoUnit.MINUTES)))
				.signWith(KEY);
		
		return PREFIX + builder.compact();
	}
	
	public static int isValidToken(String token) {
		if(!token.startsWith(PREFIX)) {
			return 3; //Other errors
		}
		try {
			Claims claims = Jwts.parserBuilder()
					.requireIssuer("ERTIS")
					.requireSubject("Blackbox API")
					.setSigningKey(KEY)
					.build()
					.parseClaimsJws(token.replace(PREFIX, ""))
					.getBody();
			int token_role = (int)claims.get("role");
			if(token_role != 1 && token_role != 2) {
				return 2; //Not valid role
			}
			return 0; //Everything ok
		} catch (ExpiredJwtException ex) {
			return 1; //Token expired
		} catch (JwtException ex) {
			return 3; //Other errors
		}
	}

}
