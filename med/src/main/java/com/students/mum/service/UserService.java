package com.students.mum.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.students.mum.repository.LoginUserRepository;

public class UserService {

	@Autowired
	private LoginUserRepository userRepository;
//	@Autowired
//	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
//    public void signUp(LoginUser user) {
//        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
//        userRepository.save(user);
//    }
}
