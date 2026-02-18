package com.tfg.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

import com.tfg.dto.ServiceDto;
import com.tfg.model.Service;
import com.tfg.model.User;
import com.tfg.repository.ServiceRepository;
import com.tfg.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceServiceImpl implements IServiceService {

	private final ServiceRepository serviceRepository;
	private final UserRepository userRepository;

	@Override
	public List<ServiceDto> getAllServices() {
		return serviceRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@Override
	public ServiceDto getServiceById(Long id) {
		Service service = serviceRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + id));
		return convertToDto(service);
	}

	@Override
	public List<ServiceDto> getServicesByProfessional(Long profesionalId) {
		return serviceRepository.findByProfesionalId(profesionalId).stream().map(this::convertToDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<ServiceDto> getServicesByCategory(String categoria) {
		return serviceRepository.findByCategoria(categoria).stream().map(this::convertToDto)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public ServiceDto createService(ServiceDto serviceDto) {
		User profesional = userRepository.findById(serviceDto.getProfesionalId())
				.orElseThrow(() -> new RuntimeException("Profesional no encontrado"));

		if (profesional.getRole() != User.UserRole.profesional) {
			throw new RuntimeException("El usuario no es un profesional");
		}
		if (!profesional.getActivo()) {
			throw new RuntimeException("El profesional está inactivo");
		}

		if (serviceDto.getTitulo() == null || serviceDto.getTitulo().isBlank()) {
			throw new IllegalArgumentException("El título es obligatorio");
		}
//		if (serviceDto.getPrecio() == null || serviceDto.getPrecio() < 0) {
//			throw new IllegalArgumentException("El precio no es válido");
//		}
		Service service = new Service();
		service.setProfesionalId(serviceDto.getProfesionalId());
		service.setTitulo(serviceDto.getTitulo());
		service.setCategoria(serviceDto.getCategoria());
		service.setDescripcion(serviceDto.getDescripcion());
		service.setPrecio(serviceDto.getPrecio());
		service.setDisponibilidad(true);

		Service savedService = serviceRepository.save(service);
		return convertToDto(savedService);
	}

	@Override
	@Transactional
	public ServiceDto updateService(Long id, ServiceDto serviceDto) {
		Service service = serviceRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + id));

		service.setTitulo(serviceDto.getTitulo());
		service.setCategoria(serviceDto.getCategoria());
		service.setDescripcion(serviceDto.getDescripcion());
		service.setPrecio(serviceDto.getPrecio());
		service.setDisponibilidad(serviceDto.getDisponibilidad());

		Service updatedService = serviceRepository.save(service);
		return convertToDto(updatedService);
	}

	@Override
	@Transactional
	public void deleteService(Long id) {
		if (!serviceRepository.existsById(id)) {
			throw new RuntimeException("Servicio no encontrado con ID: " + id);
		}
		serviceRepository.deleteById(id);
	}

	private ServiceDto convertToDto(Service service) {
		ServiceDto dto = new ServiceDto();
		dto.setId(service.getId());
		dto.setProfesionalId(service.getProfesionalId());
		dto.setTitulo(service.getTitulo());
		dto.setCategoria(service.getCategoria());
		dto.setDescripcion(service.getDescripcion());
		dto.setPrecio(service.getPrecio());
		dto.setDisponibilidad(service.getDisponibilidad());

		if (service.getProfesional() != null) {
			dto.setProfesionalNombre(service.getProfesional().getNombre());
		}

		return dto;
	}
}