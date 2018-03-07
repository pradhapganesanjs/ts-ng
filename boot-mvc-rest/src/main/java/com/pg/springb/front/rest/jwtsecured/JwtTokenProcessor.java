package com.pg.springb.front.rest.jwtsecured;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtTokenProcessor {

	/*
	@Value("${security.jwt.token.secret-key}")
	private String SECRET;

	@Value("${security.jwt.token.expires}")
	private long expiresInSecs;
	
	@Value("${security.jwt.token.prefix}")
	private String TOKEN_PREFIX;
	*/
	private String SECRET = "frontier";
	private long expiresInSecs = 300;
	private String TOKEN_PREFIX = "Bearer"; 	
	private final String HEADER_AUTH = "Authorization";

	public String generateToken(JwtUser signUser) {

		if(signUser == null) return null;
		
		Claims cl = Jwts.claims().setSubject(signUser.getUserName());
		if(null != signUser.getRoles()) {
			cl.put("roles", signUser.getRoles().toArray());
		}

		Date validTillDateTime = getExpireDatetime();
		System.out.println(" validTillDateTime " + validTillDateTime);
		String newToken = Jwts.builder().setClaims(cl).signWith(SignatureAlgorithm.HS256, SECRET)
				.setIssuedAt(new Date()).setExpiration(validTillDateTime).compact();

		System.out.println("Token : " + newToken);
		return newToken;
	}

	private Date getExpireDatetime() {
		LocalDateTime ldt = LocalDateTime.now().plusSeconds(expiresInSecs);
        // convert LocalDateTime to date
        Date validTillDateTime = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
		return validTillDateTime;
	}

	public String getToken(HttpServletRequest req) {
		String authHeader = req.getHeader(HEADER_AUTH);
		if (!StringUtils.isEmpty(authHeader) && authHeader.startsWith(TOKEN_PREFIX)) {
			String token = authHeader.substring(TOKEN_PREFIX.length(), authHeader.length()); 
			return token;
		}
		return null;
	}
	
	public Authentication getAuthentication(String token) {
		
		JwtUser user = claimUser(token);
				
		if(null != user) {
	        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
	        	if(null != user.getRoles()) {
	        		grantedAuthorities = AuthorityUtils
	        				.createAuthorityList(user.getRoles().stream().toArray(String[]::new));
	        	}
	        
			return new UsernamePasswordAuthenticationToken(user.getUserName(), null, grantedAuthorities);
			
		}else {
			return null;
		}
	}
	
	public Authentication getAuthentication(HttpServletRequest req) {
		String token = getToken(req);
		if(!StringUtils.isEmpty(token)) {
			return getAuthentication(token);
		}
		return null;
	}
	
	private String claimSubject(String token) {
        String userName = Jwts.parser()
        					.setSigningKey(SECRET)
        					.parseClaimsJws(token)
        					.getBody()
        					.getSubject();
		return userName;
	}
	private JwtUser claimUser(String token) {
		JwtUser user = new JwtUser();
		
		Claims claims = null;
		try {
			claims = Jwts.parser()
									.setSigningKey(SECRET)
									.parseClaimsJws(token)
									.getBody();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		if(null == claims) return null; 
		
		user.setUserName(claims.getSubject());
		
        List<String> roles = null != claims.get("roles") ? (ArrayList<String>)claims.get("roles") : new ArrayList<>();
        
        System.out.println(" claim roles " + roles);
        if(roles!=null) {
        	roles.forEach(each -> System.out.println(each));
        }
        
        user.setRoles(roles);
        return user;
	}
}
