package com.tfg.dto;

import com.tfg.model.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	private Long id;
	@Email
	@NotBlank
	private String email;
	@NotBlank
	private String nombre;
	private String telefono;
	private User.UserRole role;
	private String especialidad;
	private Boolean activo;
}