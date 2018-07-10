package com.beacon.analyticscollection.security;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.header}")
    private String tokenHeader;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String username = null;
        String authToken = null;
        if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
        	response.setStatus(HttpServletResponse.SC_OK);
        }else {
	        final String requestHeader = request.getHeader(this.tokenHeader);
	        if (requestHeader != null && requestHeader.startsWith("Bearer ")) {
	            authToken = requestHeader.substring(7);
	            try {
	                username = jwtTokenUtil.getUsernameFromToken(authToken);
	            } catch (IllegalArgumentException e) {
	                logger.error("an error occurred during getting username from token", e);
	            } catch (ExpiredJwtException e) {
	                logger.warn("the token is expired and not valid anymore", e);
	            }
	        } else {
	            logger.warn("couldn't find bearer string, will ignore the header");
	        }        
	        logger.info("checking authentication for user " + username);
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // It is not compelling necessary to load the use details from the database. You could also store the information
            // in the token and read it from it.
            //UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
        	
        	JwtUserDto parsedUser = jwtTokenUtil.parseToken(authToken);

            // For simple validation it is completely sufficient to just check the token integrity. You don't have to call
            // the database compellingly.
            
        	List<GrantedAuthority> authorityList = AuthorityUtils.commaSeparatedStringToAuthorityList(parsedUser.getRole());
        	AuthenticatedUser userDetails = new AuthenticatedUser(parsedUser.getId(), parsedUser.getUsername(), authToken, authorityList);
        	
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            logger.info("authenticated user " + username + ", setting security context");
            SecurityContextHolder.getContext().setAuthentication(authentication);
            
        }

        chain.doFilter(request, response);
    }
}
