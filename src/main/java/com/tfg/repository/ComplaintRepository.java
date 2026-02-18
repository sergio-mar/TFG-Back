package com.tfg.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tfg.model.Complaint;

@Repository
public interface ComplaintRepository extends JpaRepository<Complaint, Long> {
	List<Complaint> findByUsuarioId(Long usuarioId);

	List<Complaint> findByEstado(Complaint.ComplaintStatus estado);
}