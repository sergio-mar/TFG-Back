package com.tfg.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tfg.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	List<Booking> findByClienteId(Long clienteId);

	List<Booking> findByServicioId(Long servicioId);

	List<Booking> findByEstado(Booking.BookingStatus estado);

	List<Booking> findByFecha(LocalDate fecha);

	@Query("SELECT b FROM Booking b WHERE b.servicio.profesionalId = :profesionalId")
	List<Booking> findByProfesionalId(Long profesionalId);
}