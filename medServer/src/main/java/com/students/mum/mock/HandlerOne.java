package com.students.mum.mock;

import java.util.Arrays;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.students.mum.domain.LoginUser;
import com.students.mum.domain.Role;
import com.students.mum.repository.LoginUserRepository;
import com.students.mum.repository.RoleRepository;

@Service

@Transactional
public class HandlerOne {

	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private LoginUserRepository loginUserRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public void handleAll() {
		Role admin = createAdminRole();
		LoginUser user2 = new LoginUser();
		user2.setUsername("2");
		user2.setPassword(bCryptPasswordEncoder.encode("2"));
		user2.getRoles().addAll(Arrays.asList(admin));
		loginUserRepository.save(user2);
	}

	public Role createAdminRole() {
		return createRole("ADMIN");

	}

	public Role createFacultyRole() {
		return createRole("FACULTY");
	}

	public Role createStudentRole() {
		return createRole("STUDENT");
	}

	private Role createRole(String roleName) {
		Role r = new Role();
		r.setRole(roleName);
		return roleRepository.save(r);
	}

}
