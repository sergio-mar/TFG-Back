package com.tfg.service;

import com.tfg.dto.BookingDto;
import com.tfg.model.Booking;
import com.tfg.model.User;
import com.tfg.model.Service;
import com.tfg.repository.BookingRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookingServiceImplTest {

	@Mock
	private BookingRepository bookingRepository;

	@InjectMocks
	private BookingServiceImpl bookingService;

	private Booking booking;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		booking = new Booking();
		booking.setId(1L);
		booking.setClienteId(5L);
		booking.setServicioId(10L);
		booking.setFecha(LocalDate.now().plusDays(1));
		booking.setEstado(Booking.BookingStatus.pendiente);

		User cliente = new User();
		cliente.setNombre("Cliente Test");
		booking.setCliente(cliente);

		Service servicio = new Service();
		servicio.setTitulo("Servicio Test");
		booking.setServicio(servicio);
	}

	@Test
	void getAllBookings_ok() {
		when(bookingRepository.findAll()).thenReturn(List.of(booking));

		List<BookingDto> result = bookingService.getAllBookings();

		assertEquals(1, result.size());
		assertEquals("Cliente Test", result.get(0).getClienteNombre());
	}

	@Test
	void getBookingById_ok() {
		when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

		BookingDto dto = bookingService.getBookingById(1L);

		assertEquals(5L, dto.getClienteId());
		assertEquals("Servicio Test", dto.getServicioTitulo());
	}

	@Test
	void getBookingById_notFound() {
		when(bookingRepository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class, () -> bookingService.getBookingById(99L));
	}

	@Test
	void getBookingsByClient_ok() {
		when(bookingRepository.findByClienteId(5L)).thenReturn(List.of(booking));

		List<BookingDto> result = bookingService.getBookingsByClient(5L);

		assertEquals(1, result.size());
		assertEquals(5L, result.get(0).getClienteId());
	}

	@Test
	void getBookingsByProfessional_ok() {
		when(bookingRepository.findByProfesionalId(10L)).thenReturn(List.of(booking));

		List<BookingDto> result = bookingService.getBookingsByProfessional(10L);

		assertEquals(1, result.size());
		assertEquals("Servicio Test", result.get(0).getServicioTitulo());
	}

	@Test
	void createBooking_ok() {
		BookingDto dto = new BookingDto();
		dto.setClienteId(5L);
		dto.setServicioId(10L);
		dto.setFecha(LocalDate.now().plusDays(2));

		when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

		BookingDto result = bookingService.createBooking(dto);

		assertEquals(5L, result.getClienteId());
		assertEquals("Servicio Test", result.getServicioTitulo());
	}

	@Test
	void createBooking_fechaNula() {
		BookingDto dto = new BookingDto();
		dto.setClienteId(5L);
		dto.setServicioId(10L);
		dto.setFecha(null);

		assertThrows(IllegalArgumentException.class, () -> bookingService.createBooking(dto));
	}

	@Test
	void createBooking_fechaPasada() {
		BookingDto dto = new BookingDto();
		dto.setClienteId(5L);
		dto.setServicioId(10L);
		dto.setFecha(LocalDate.now().minusDays(1));

		assertThrows(IllegalArgumentException.class, () -> bookingService.createBooking(dto));
	}

	@Test
	void updateBookingStatus_ok() {
		when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
		when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

		BookingDto result = bookingService.updateBookingStatus(1L, Booking.BookingStatus.aceptado);

		assertEquals(Booking.BookingStatus.aceptado, result.getEstado());
	}

	@Test
	void updateBookingStatus_notFound() {
		when(bookingRepository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(RuntimeException.class,
				() -> bookingService.updateBookingStatus(99L, Booking.BookingStatus.cancelado));
	}

	@Test
	void getBookingsByStatus_ok() {
		when(bookingRepository.findByEstado(Booking.BookingStatus.pendiente)).thenReturn(List.of(booking));

		List<BookingDto> result = bookingService.getBookingsByStatus(Booking.BookingStatus.pendiente);

		assertEquals(1, result.size());
		assertEquals(Booking.BookingStatus.pendiente, result.get(0).getEstado());
	}
}