package com.tfg.service;

import com.tfg.dto.ServiceDto;
import com.tfg.model.Service;
import com.tfg.model.User;
import com.tfg.repository.ServiceRepository;
import com.tfg.repository.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceServiceImplTest {

	@Mock
	private ServiceRepository serviceRepository;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private ServiceServiceImpl serviceService;

	private User profesional;
	private Service service;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		profesional = new User();
		profesional.setId(10L);
		profesional.setNombre("Profesional Test");
		profesional.setRole(User.UserRole.profesional);
		profesional.setActivo(true);

		service = new Service();
		service.setId(1L);
		service.setProfesionalId(10L);
		service.setTitulo("Servicio Test");
		service.setCategoria("Belleza");
		service.setDescripcion("Descripción");
		service.setPrecio(new BigDecimal("50.00"));
		service.setDisponibilidad(true);
		service.setProfesional(profesional);
	}

	@Test
	void getAllServices_ok() {
		when(serviceRepository.findAll()).thenReturn(List.of(service));

		List<ServiceDto> result = serviceService.getAllServices();

		assertEquals(1, result.size());
		assertEquals("Servicio Test", result.get(0).getTitulo());
		assertEquals(new BigDecimal("50.00"), result.get(0).getPrecio());
	}

	@Test
	void getServiceById_ok() {
		when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));

		ServiceDto dto = serviceService.getServiceById(1L);

		assertEquals("Servicio Test", dto.getTitulo());
		assertEquals(new BigDecimal("50.00"), dto.getPrecio());
	}

	@Test
	void getServiceById_notFound() {
		when(serviceRepository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> serviceService.getServiceById(99L));
	}

	@Test
	void getServicesByProfessional_ok() {
		when(serviceRepository.findByProfesionalId(10L)).thenReturn(List.of(service));

		List<ServiceDto> result = serviceService.getServicesByProfessional(10L);

		assertEquals(1, result.size());
		assertEquals("Servicio Test", result.get(0).getTitulo());
	}

	@Test
	void getServicesByCategory_ok() {
		when(serviceRepository.findByCategoria("Belleza")).thenReturn(List.of(service));

		List<ServiceDto> result = serviceService.getServicesByCategory("Belleza");

		assertEquals(1, result.size());
		assertEquals("Belleza", result.get(0).getCategoria());
	}

	@Test
	void createService_ok() {
		ServiceDto dto = new ServiceDto();
		dto.setProfesionalId(10L);
		dto.setTitulo("Nuevo Servicio");
		dto.setCategoria("Belleza");
		dto.setDescripcion("Desc");
		dto.setPrecio(new BigDecimal("30.00"));

		when(userRepository.findById(10L)).thenReturn(Optional.of(profesional));
		when(serviceRepository.save(any(Service.class))).thenReturn(service);

		ServiceDto result = serviceService.createService(dto);

		assertEquals("Servicio Test", result.getTitulo());
		assertEquals(new BigDecimal("50.00"), result.getPrecio());
	}

	@Test
	void createService_profesionalNoExiste() {
		ServiceDto dto = new ServiceDto();
		dto.setProfesionalId(99L);

		when(userRepository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> serviceService.createService(dto));
	}

	@Test
	void createService_usuarioNoProfesional() {
		profesional.setRole(User.UserRole.cliente);

		ServiceDto dto = new ServiceDto();
		dto.setProfesionalId(10L);

		when(userRepository.findById(10L)).thenReturn(Optional.of(profesional));

		assertThrows(RuntimeException.class, () -> serviceService.createService(dto));
	}

	@Test
	void createService_profesionalInactivo() {
		profesional.setActivo(false);

		ServiceDto dto = new ServiceDto();
		dto.setProfesionalId(10L);

		when(userRepository.findById(10L)).thenReturn(Optional.of(profesional));

		assertThrows(RuntimeException.class, () -> serviceService.createService(dto));
	}

	@Test
	void createService_tituloInvalido() {
		ServiceDto dto = new ServiceDto();
		dto.setProfesionalId(10L);
		dto.setTitulo("   ");

		when(userRepository.findById(10L)).thenReturn(Optional.of(profesional));

		assertThrows(IllegalArgumentException.class, () -> serviceService.createService(dto));
	}

	@Test
	void updateService_ok() {
		ServiceDto dto = new ServiceDto();
		dto.setTitulo("Actualizado");
		dto.setCategoria("Nueva");
		dto.setDescripcion("Desc");
		dto.setPrecio(new BigDecimal("99.00"));
		dto.setDisponibilidad(false);

		when(serviceRepository.findById(1L)).thenReturn(Optional.of(service));
		when(serviceRepository.save(any(Service.class))).thenReturn(service);

		ServiceDto result = serviceService.updateService(1L, dto);

		assertEquals("Actualizado", result.getTitulo());
		assertEquals(new BigDecimal("99.00"), result.getPrecio());
	}

	@Test
	void updateService_notFound() {
		when(serviceRepository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> serviceService.updateService(99L, new ServiceDto()));
	}

	@Test
	void deleteService_ok() {
		when(serviceRepository.existsById(1L)).thenReturn(true);

		serviceService.deleteService(1L);

		verify(serviceRepository).deleteById(1L);
	}

	@Test
	void deleteService_notFound() {
		when(serviceRepository.existsById(99L)).thenReturn(false);

		assertThrows(RuntimeException.class, () -> serviceService.deleteService(99L));
	}
}