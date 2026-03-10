package com.tfg.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.dto.UserDto;
import com.tfg.model.User;
import com.tfg.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements IUserService, UserDetailsService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

		if (!Boolean.TRUE.equals(user.getActivo())) {
			throw new UsernameNotFoundException("Usuario inactivo: " + username);
		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				List.of(new SimpleGrantedAuthority(user.getRole().name())));
	}

	@Override
	public List<UserDto> getAllUsers() {
		return userRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@Override
	public UserDto getUserById(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
		return convertToDto(user);
	}

	@Override
	public User getUserEntityByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
	}

	@Override
	@Transactional
	public UserDto createUser(User user) {
		if (userRepository.existsByEmail(user.getEmail())) {
			throw new RuntimeException("El email ya está registrado");
		}
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setActivo(true);
		User savedUser = userRepository.save(user);
		return convertToDto(savedUser);
	}

	@Override
	@Transactional
	public UserDto updateUser(Long id, UserDto userDto) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));

		user.setNombre(userDto.getNombre());
		user.setTelefono(userDto.getTelefono());
		if (userDto.getEspecialidad() != null) {
			user.setEspecialidad(userDto.getEspecialidad());
		}
		user.setRole(userDto.getRole());
		user.setActivo(userDto.getActivo());

		User updatedUser = userRepository.save(user);
		return convertToDto(updatedUser);
	}

	@Override
	@Transactional
	public void deleteUser(Long id) {
		User user = userRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
		user.setActivo(false);
		userRepository.save(user);
	}

	@Override
	public List<UserDto> getUsersByRole(User.UserRole role) {
		return userRepository.findByRole(role).stream().map(this::convertToDto).collect(Collectors.toList());
	}

	private UserDto convertToDto(User user) {
		UserDto dto = new UserDto();
		dto.setId(user.getId());
		dto.setEmail(user.getEmail());
		dto.setNombre(user.getNombre());
		dto.setTelefono(user.getTelefono());
		dto.setRole(user.getRole());
		dto.setEspecialidad(user.getEspecialidad());
		dto.setActivo(user.getActivo());
		return dto;
	}
}