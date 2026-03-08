package com.tfg.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.tfg.dto.LoginRequest;
import com.tfg.dto.LoginResponse;
import com.tfg.dto.UserDto;
import com.tfg.model.User;
import com.tfg.service.IUserService;
import com.tfg.service.JwtService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final JwtService jwtService;
	private final IUserService userService;
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@PostMapping("/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

			UserDetails userDetails = (UserDetails) authentication.getPrincipal();
			String jwt = jwtService.generateToken(userDetails);

			User user = userService.getUserEntityByEmail(loginRequest.getEmail());
			
			UserDto userDto = new UserDto(user.getId(), user.getEmail(), user.getNombre(), user.getTelefono(),
					user.getRole(), user.getEspecialidad(), user.getActivo());
			
			logger.info("Login del usuario: {}", loginRequest.getEmail());
			return ResponseEntity.ok(new LoginResponse(jwt, userDto));
		} catch (BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error en el servidor");
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@Valid @RequestBody User user) {
		try {
			logger.info("Registro del usuario: {}", user.getEmail());
			return ResponseEntity.ok(userService.createUser(user));
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}
}