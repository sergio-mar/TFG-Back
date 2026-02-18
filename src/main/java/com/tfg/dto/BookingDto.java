package com.tfg.dto;

import java.time.LocalDate;

import com.tfg.model.Booking;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {

	private Long id;
	@NotNull
	private Long clienteId;
	@NotNull
	private Long servicioId;
	@NotNull
	@Future
	private LocalDate fecha;
	private Booking.BookingStatus estado;
	private Integer valoracion;
	private String comentario;
	private String clienteNombre;
	private String servicioTitulo;
}