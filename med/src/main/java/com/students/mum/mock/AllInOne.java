package com.students.mum.mock;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.students.mum.domain.LoginUser;
import com.students.mum.repository.LoginUserRepository;
import com.students.mum.repository.RoleRepository;
import com.students.mum.service.UserService;

@Service
public class AllInOne {
	
	@Autowired 
	LoginUserRepository loginUserRepository;
	
	@Autowired 
	RoleRepository roleRepository;
	@Autowired
	UserService userService;
	@PostConstruct
	public void createData() {
		LoginUser user = new LoginUser();
		user.setUsername("123");
		user.setPassword("123");
		user.setRoles(roleRepository.findAll());
//		loginUserRepository.save(user);
		userService.signUp(user);
	}

}
