package com.students.mum.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class UserWithTag extends User {

	public UserWithTag(String username, String password, Collection<? extends GrantedAuthority> authorities,
			String tag) {
		super(username, password, authorities);
		this.tag = tag;
	}

	private final String tag;

	public String getTag() {
		return tag;
	}

	public UserWithTag(String username, String password, boolean enabled, boolean accountNonExpired,
			boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities,
			String tag) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
		this.tag = tag;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -2602047497645776022L;

}
