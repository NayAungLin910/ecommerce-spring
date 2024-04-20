package com.ecommerce.javaspring.ecom.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	public transient static final String SECRET = "3EBBCF4FF5CEC27AB8C7D8A61D9F0FA038E462D6E5E13CE2B9C3C493A1D19C9C";

	public String generateToken(String userName) {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userName);
	}

	private String createToken(Map<String, Object> claims, String userName) {
		return Jwts.builder().setClaims(claims).setSubject(userName).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 10000 * 60 * 30))
				.signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
	}

	private SecretKey getSignKey() {
		byte[] keybytes = Decoders.BASE64.decode(SECRET);
		return Keys.hmacShaKeyFor(keybytes);
	}

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody();
	}
	
	public Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String userName = extractUsername(token);
		return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}
}
