package com.tfg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfg.model.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Long> {
	List<Service> findByProfesionalId(Long profesionalId);

	List<Service> findByCategoria(String categoria);

	List<Service> findByDisponibilidad(Boolean disponibilidad);

	List<Service> findByTituloContainingIgnoreCase(String titulo);
}