package com.tfg.dto;

import com.tfg.model.User;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String email;
	private String nombre;
	private User.UserRole role;

	public LoginResponse(String token, User user) {
		this.token = token;
		this.id = user.getId();
		this.email = user.getEmail();
		this.nombre = user.getNombre();
		this.role = user.getRole();
	}
	public LoginResponse(String token, UserDto user) {
		this.token = token;
		this.id = user.getId();
		this.email = user.getEmail();
		this.nombre = user.getNombre();
		this.role = user.getRole();
	}
}