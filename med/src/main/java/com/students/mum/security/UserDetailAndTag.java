package com.students.mum.security;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDetailAndTag extends UserDetails {
	String getTag();
}
