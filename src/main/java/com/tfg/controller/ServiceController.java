package com.tfg.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tfg.dto.ServiceDto;
import com.tfg.service.IServiceService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/services")
@RequiredArgsConstructor
public class ServiceController {

	private final IServiceService serviceService;

	@GetMapping
	public ResponseEntity<List<ServiceDto>> getAllServices() {
		return ResponseEntity.ok(serviceService.getAllServices());
	}

	@GetMapping("/{id}")
	public ResponseEntity<ServiceDto> getServiceById(@PathVariable Long id) {
		return ResponseEntity.ok(serviceService.getServiceById(id));
	}

	@GetMapping("/professional/{profesionalId}")
	public ResponseEntity<List<ServiceDto>> getServicesByProfessional(@PathVariable Long profesionalId) {
		return ResponseEntity.ok(serviceService.getServicesByProfessional(profesionalId));
	}

	@GetMapping("/category/{categoria}")
	public ResponseEntity<List<ServiceDto>> getServicesByCategory(@PathVariable String categoria) {
		return ResponseEntity.ok(serviceService.getServicesByCategory(categoria));
	}

	@PostMapping
	public ResponseEntity<ServiceDto> createService(@Valid @RequestBody ServiceDto serviceDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(serviceService.createService(serviceDto));
	}

	@PutMapping("/{id}")
	public ResponseEntity<ServiceDto> updateService(@PathVariable Long id, @Valid @RequestBody ServiceDto serviceDto) {
		return ResponseEntity.ok(serviceService.updateService(id, serviceDto));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteService(@PathVariable Long id) {
		serviceService.deleteService(id);
		return ResponseEntity.noContent().build();
	}
}