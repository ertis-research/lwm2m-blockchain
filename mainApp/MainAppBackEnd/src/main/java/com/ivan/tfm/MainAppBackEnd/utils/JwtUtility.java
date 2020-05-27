package com.ivan.tfm.MainAppBackEnd.utils;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.crypto.SecretKey;

import com.ivan.tfm.MainAppBackEnd.beans.Credentials;

import io.jsonwebtoken.Claims;
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
	
	public static boolean isValidToken(String token, int role) {
		if(!token.startsWith(PREFIX)) {
			return false;
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
			if(role < token_role) {
				return false;
			}
			return true;
		} catch (JwtException ex) {
			return false;
		}
	}
	
	/// Just to test, understand how this utility class works
	public static void main(String args[]) {
		String token = generateToken(new Credentials("JTrillo", "trillo@uma.es", 2, "password"));
		System.out.println(token);
		System.out.println(isValidToken(token, 2));
	}
}
