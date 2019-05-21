package com.students.mum.security;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.students.mum.domain.LoginUser;
import com.students.mum.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		LoginUser loginUser = userService.findByUsername(username);
		if (loginUser == null) {
			throw new UsernameNotFoundException(username);
		}
		return new UserWithTag(
				loginUser.getUsername(), loginUser.getPassword(), loginUser.getRoles().stream()
						.map(r -> new SimpleGrantedAuthority(r.getRole())).collect(Collectors.toList()),
				loginUser.getTag());
	}
}
