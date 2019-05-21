package com.students.mum.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.students.mum.domain.LoginUser;
import com.students.mum.repository.LoginUserRepository;

@Service
public class UserService {

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public void signUp(LoginUser user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		loginUserRepository.save(user);
	}

	@Autowired
	private LoginUserRepository loginUserRepository;

	@Transactional
	public LoginUser findByUsername(String username) {
		LoginUser user = loginUserRepository.findByUsername(username).orElse(null);
		if(user!=null)
		{
			user.getRoles().forEach((o)->{});
		}
		return user;
	}
}
