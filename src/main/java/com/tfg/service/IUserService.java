package com.tfg.service;

import java.util.List;

import com.tfg.dto.UserDto;
import com.tfg.model.User;

public interface IUserService {
	List<UserDto> getAllUsers();

	UserDto getUserById(Long id);

	User getUserEntityByEmail(String email);

	UserDto createUser(User user);

	UserDto updateUser(Long id, UserDto userDto);

	void deleteUser(Long id);

	List<UserDto> getUsersByRole(User.UserRole role);
}