package com.tfg.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "complaints")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Complaint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotNull
	@Column(name = "usuario_id", nullable = false)
	private Long usuarioId;

	@NotBlank
	@Column(nullable = false)
	private String asunto;

	@NotBlank
	@Column(nullable = false, columnDefinition = "TEXT")
	private String descripcion;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private ComplaintStatus estado = ComplaintStatus.pendiente;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_id", insertable = false, updatable = false)
	private User usuario;

	public enum ComplaintStatus {
		pendiente, en_revision, resuelta
	}
}