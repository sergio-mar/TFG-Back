package com.tfg.service;

import com.tfg.dto.UserDto;
import com.tfg.model.User;
import com.tfg.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;

	@Mock
	private PasswordEncoder passwordEncoder;

	@InjectMocks
	private UserServiceImpl userService;

	private User user;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		user = new User();
		user.setId(1L);
		user.setEmail("test@test.com");
		user.setPassword("1234");
		user.setNombre("Test User");
		user.setTelefono("600000000");
		user.setRole(User.UserRole.cliente);
		user.setActivo(true);
	}

	@Test
	void loadUserByUsername_ok() {
		when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

		var result = userService.loadUserByUsername("test@test.com");

		assertEquals("test@test.com", result.getUsername());
		assertTrue(result.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("cliente")));
	}

	@Test
	void loadUserByUsername_userNotFound() {
		when(userRepository.findByEmail("missing@test.com")).thenReturn(Optional.empty());

		assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("missing@test.com"));
	}

	@Test
	void loadUserByUsername_userInactive() {
		user.setActivo(false);
		when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

		assertThrows(UsernameNotFoundException.class, () -> userService.loadUserByUsername("test@test.com"));
	}

	@Test
	void getAllUsers_ok() {
		when(userRepository.findAll()).thenReturn(List.of(user));

		List<UserDto> result = userService.getAllUsers();

		assertEquals(1, result.size());
		assertEquals("test@test.com", result.get(0).getEmail());
	}

	@Test
	void getUserById_ok() {
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		UserDto dto = userService.getUserById(1L);

		assertEquals("test@test.com", dto.getEmail());
	}

	@Test
	void getUserById_notFound() {
		when(userRepository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> userService.getUserById(99L));
	}

	@Test
	void getUserEntityByEmail_ok() {
		when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));

		User result = userService.getUserEntityByEmail("test@test.com");

		assertEquals("test@test.com", result.getEmail());
	}

	@Test
	void getUserEntityByEmail_notFound() {
		when(userRepository.findByEmail("missing@test.com")).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> userService.getUserEntityByEmail("missing@test.com"));
	}

	@Test
	void createUser_ok() {
		when(userRepository.existsByEmail("test@test.com")).thenReturn(false);
		when(passwordEncoder.encode("1234")).thenReturn("encoded");
		when(userRepository.save(any(User.class))).thenReturn(user);

		UserDto dto = userService.createUser(user);

		assertEquals("test@test.com", dto.getEmail());
		verify(passwordEncoder).encode("1234");
	}

	@Test
	void createUser_emailExists() {
		when(userRepository.existsByEmail("test@test.com")).thenReturn(true);

		assertThrows(RuntimeException.class, () -> userService.createUser(user));
	}

	@Test
	void updateUser_ok() {
		UserDto dto = new UserDto();
		dto.setNombre("Nuevo");
		dto.setTelefono("611111111");
		dto.setRole(User.UserRole.profesional);
		dto.setActivo(true);

		when(userRepository.findById(1L)).thenReturn(Optional.of(user));
		when(userRepository.save(any(User.class))).thenReturn(user);

		UserDto result = userService.updateUser(1L, dto);

		assertEquals("Nuevo", result.getNombre());
		assertEquals("611111111", result.getTelefono());
	}

	@Test
	void updateUser_notFound() {
		when(userRepository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> userService.updateUser(99L, new UserDto()));
	}

	@Test
	void deleteUser_ok() {
		when(userRepository.findById(1L)).thenReturn(Optional.of(user));

		userService.deleteUser(1L);

		assertFalse(user.getActivo());
		verify(userRepository).save(user);
	}

	@Test
	void deleteUser_notFound() {
		when(userRepository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> userService.deleteUser(99L));
	}

	@Test
	void getUsersByRole_ok() {
		when(userRepository.findByRole(User.UserRole.cliente)).thenReturn(List.of(user));

		List<UserDto> result = userService.getUsersByRole(User.UserRole.cliente);

		assertEquals(1, result.size());
		assertEquals("test@test.com", result.get(0).getEmail());
	}
}