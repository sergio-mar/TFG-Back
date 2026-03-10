package com.tfg.dto;

import com.tfg.model.User.UserRole;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RegisterRequest {

	@NotBlank(message = "El nombre es obligatorio")
	@Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
	private String nombre;

	@NotBlank(message = "El email es obligatorio")
	@Email(message = "El formato del email no es válido")
	@Size(max = 150, message = "El email no puede superar los 150 caracteres")
	private String email;

	@NotBlank(message = "La contraseña es obligatoria")
	@Size(min = 8, max = 64, message = "La contraseña debe tener entre 8 y 64 caracteres")
	@Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "La contraseña debe contener al menos una mayúscula, una minúscula y un número")
	private String password;

	@Pattern(regexp = "^[679]\\d{8}$", message = "El teléfono debe ser un número español válido de 9 dígitos empezando por 6, 7 o 9")
	private String telefono;

	@NotNull(message = "El rol es obligatorio")
	private UserRole role;

	private String especialidad;

	public RegisterRequest(
			@NotBlank(message = "El nombre es obligatorio") @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres") String nombre,
			@NotBlank(message = "El email es obligatorio") @Email(message = "El formato del email no es válido") @Size(max = 150, message = "El email no puede superar los 150 caracteres") String email,
			@NotBlank(message = "La contraseña es obligatoria") @Size(min = 8, max = 64, message = "La contraseña debe tener entre 8 y 64 caracteres") @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).*$", message = "La contraseña debe contener al menos una mayúscula, una minúscula y un número") String password,
			@Pattern(regexp = "^[679]\\d{8}$", message = "El teléfono debe ser un número español válido de 9 dígitos empezando por 6, 7 o 9") String telefono,
			@NotNull(message = "El rol es obligatorio") UserRole role) {
		super();
		this.nombre = nombre;
		this.email = email;
		this.password = password;
		this.telefono = telefono;
		this.role = role;
	}
}