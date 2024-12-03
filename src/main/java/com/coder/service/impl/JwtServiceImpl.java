package com.coder.service.impl;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import com.coder.entity.User;
import com.coder.service.JwtService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

	private String secretKey ="";
	public JwtServiceImpl() {
		
		try {
			
			KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
			SecretKey sKey = keyGenerator.generateKey();
			secretKey = Base64.getEncoder().encodeToString(sKey.getEncoded());
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public String generateToken(User user) {
		
		Map<String, Object> claims = new HashMap<>();
		
		claims.put("id", user.getId());
		claims.put("role", user.getRoles());
		claims.put("status", user.getStatus().getIsActive());
		
		String token = Jwts.builder().claims().add(claims).subject(user.getEmail())
						.issuedAt(new Date(System.currentTimeMillis()))
						.expiration(new Date(System.currentTimeMillis()+60*60*10))
						.and()
						.signWith(getKey())
						.compact();
		return token;
	}

	private Key getKey() {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyBytes);
	}

}
