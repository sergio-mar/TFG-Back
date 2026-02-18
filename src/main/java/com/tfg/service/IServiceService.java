package com.tfg.service;

import java.util.List;

import com.tfg.dto.ServiceDto;

public interface IServiceService {
	List<ServiceDto> getAllServices();

	ServiceDto getServiceById(Long id);

	List<ServiceDto> getServicesByProfessional(Long profesionalId);

	List<ServiceDto> getServicesByCategory(String categoria);

	ServiceDto createService(ServiceDto serviceDto);

	ServiceDto updateService(Long id, ServiceDto serviceDto);

	void deleteService(Long id);
}