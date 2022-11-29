package com.junittest.work.services;

import java.util.List;

import com.junittest.work.entities.User;

public interface UserService {

	List<User> findAll();
	User findById(Long id);
	User insert(User obj);
	User update(Long id, User obj);
	void delete(Long id);
}
