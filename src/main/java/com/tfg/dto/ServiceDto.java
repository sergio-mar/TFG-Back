package com.tfg.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServiceDto {

	private Long id;
	@NotNull
	private Long profesionalId;
	@NotBlank
	private String titulo;
	@NotBlank
	private String categoria;
	private String descripcion;
	@NotNull
	@Positive
	private BigDecimal precio;
	private Boolean disponibilidad = true;
	private String profesionalNombre;
}