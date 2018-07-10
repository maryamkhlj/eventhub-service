package com.beacon.analyticscollection.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class JwtTokenUtil implements Serializable {
	
	private static final long serialVersionUID = 7216853252858948713L;

	@Value("${jwt.secret}")
    private String secret;
	
	public JwtUserDto parseToken(String token) {
        JwtUserDto u = null;

        try {
            Claims body = Jwts.parser()
                    .setSigningKey(secret.getBytes())
                    .parseClaimsJws(token)
                    .getBody();

            u = new JwtUserDto();
            u.setUsername(body.getSubject());
            u.setId((String) body.get("userid"));
            if(body.get("role") instanceof String) {
            	u.setRole((String) body.get("role"));
            } else {
            	List<String> role = (List<String>) body.get("role");
            	u.setRole(role.stream().collect(Collectors.joining(",")));
            }

        } catch (JwtException e) {
            // Simply print the exception and null will be returned for the userDto
            e.printStackTrace();
        }
        return u;
    }
 
	public String getUsernameFromToken(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }
	
	public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }
	
	public String getUserIdFromToken(String token) {
		return parseToken(token).getId();
	}
	
	private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .setSigningKey(secret.getBytes())
                .parseClaimsJws(token)
                .getBody();
    }
}
