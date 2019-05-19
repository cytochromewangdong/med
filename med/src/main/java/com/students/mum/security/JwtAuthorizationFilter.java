package com.students.mum.security;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.SignatureException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

	private static final Logger log = LoggerFactory.getLogger(JwtAuthorizationFilter.class);

	public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		String token = getCookieToken(request);
		UsernamePasswordAuthenticationToken authentication = getAuthentication(request, token);

		if (StringUtils.isEmpty(token)) {
			filterChain.doFilter(request, response);
			return;
		}

		SecurityContextHolder.getContext().setAuthentication(authentication);
		filterChain.doFilter(request, response);
	}

	private String getCookieToken(HttpServletRequest request) {
		String token = null;
		if (request.getCookies() == null) {
			return null;
		}
		for (Cookie c : request.getCookies()) {
			if (SecurityConstants.TOKEN_COOKIE_NAME.equals(c.getName())) {
				token = c.getValue();
				break;
			}
		}
		return token;
	}

	private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, String token) {
		if (StringUtils.isNotEmpty(token)) {
			try {
				byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

				Jws<Claims> parsedToken = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token);

				String username = parsedToken.getBody().getSubject();

				List<GrantedAuthority> authorities = ((List<?>) parsedToken.getBody().get("rol")).stream()
						.map(authority -> new SimpleGrantedAuthority((String) authority)).collect(Collectors.toList());
				String tag = (String) parsedToken.getBody().get("tag");
//				authorities.add(new SimpleGrantedAuthority("ROLE_FUCK"));
				if (StringUtils.isNotEmpty(username)) {

					return new UsernamePasswordAuthenticationToken(new UserDetailAndTag() {

						/**
						 * 
						 */
						private static final long serialVersionUID = 4144078882077666890L;

						@Override
						public Collection<? extends GrantedAuthority> getAuthorities() {
							return authorities;
						}

						@Override
						public String getPassword() {
							return null;
						}

						@Override
						public String getUsername() {
							return username;
						}

						@Override
						public boolean isAccountNonExpired() {

							return true;
						}

						@Override
						public boolean isAccountNonLocked() {

							return true;
						}

						@Override
						public boolean isCredentialsNonExpired() {
							return true;
						}

						@Override
						public boolean isEnabled() {
							return true;
						}

						@Override
						public String getTag() {
							return tag;
						}

					}, null, authorities);
				}
			} catch (ExpiredJwtException exception) {
				log.warn("Request to parse expired JWT : {} failed : {}", token, exception.getMessage());
			} catch (UnsupportedJwtException exception) {
				log.warn("Request to parse unsupported JWT : {} failed : {}", token, exception.getMessage());
			} catch (MalformedJwtException exception) {
				log.warn("Request to parse invalid JWT : {} failed : {}", token, exception.getMessage());
			} catch (SignatureException exception) {
				log.warn("Request to parse JWT with invalid signature : {} failed : {}", token, exception.getMessage());
			} catch (IllegalArgumentException exception) {
				log.warn("Request to parse empty or null JWT : {} failed : {}", token, exception.getMessage());
			}
		}

		return null;
	}
}
