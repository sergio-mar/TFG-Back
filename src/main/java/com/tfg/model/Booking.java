package com.tfg.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Booking {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "cliente_id", nullable = false)
	private Long clienteId;

	@NotNull
	@Column(name = "servicio_id", nullable = false)
	private Long servicioId;

	@NotNull
	@Column(nullable = false)
	private LocalDate fecha;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private BookingStatus estado = BookingStatus.pendiente;

	@Min(1)
	@Max(5)
	private Integer valoracion;

	@Column(columnDefinition = "TEXT")
	private String comentario;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cliente_id", insertable = false, updatable = false)
	private User cliente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "servicio_id", insertable = false, updatable = false)
	private Service servicio;

	public enum BookingStatus {
		pendiente, aceptado, en_curso, finalizado, cancelado
	}
}