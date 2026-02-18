package com.tfg.controller;

import com.tfg.model.Complaint;
import com.tfg.repository.ComplaintRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
public class ComplaintController {

	private final ComplaintRepository complaintRepository;

	@GetMapping
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<List<Complaint>> getAllComplaints() {
		return ResponseEntity.ok(complaintRepository.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Complaint> getComplaintById(@PathVariable Long id) {
		return complaintRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public ResponseEntity<Complaint> createComplaint(@RequestBody Complaint complaint) {
		complaint.setEstado(Complaint.ComplaintStatus.pendiente);
		return ResponseEntity.status(HttpStatus.CREATED).body(complaintRepository.save(complaint));
	}

	@PutMapping("/{id}/status")
	@PreAuthorize("hasAuthority('admin')")
	public ResponseEntity<Complaint> updateComplaintStatus(@PathVariable Long id,
			@RequestBody Complaint.ComplaintStatus status) {
		return complaintRepository.findById(id).map(complaint -> {
			complaint.setEstado(status);
			return ResponseEntity.ok(complaintRepository.save(complaint));
		}).orElse(ResponseEntity.notFound().build());
	}
}