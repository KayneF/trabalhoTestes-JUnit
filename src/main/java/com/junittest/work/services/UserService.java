package com.junittest.work.services;

import java.util.List;

import com.junittest.work.entities.User;
import com.junittest.work.entities.dto.UserDTO;

public interface UserService {

	List<User> findAll();
	User findById(Long id);
	User insert(UserDTO obj);
	User update(Long id, UserDTO obj);
	void delete(Long id);
}
