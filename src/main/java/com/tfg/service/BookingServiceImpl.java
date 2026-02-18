package com.tfg.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tfg.dto.BookingDto;
import com.tfg.model.Booking;
import com.tfg.repository.BookingRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements IBookingService {

	private final BookingRepository bookingRepository;

	@Override
	public List<BookingDto> getAllBookings() {
		return bookingRepository.findAll().stream().map(this::convertToDto).collect(Collectors.toList());
	}

	@Override
	public BookingDto getBookingById(Long id) {
		Booking booking = bookingRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + id));
		return convertToDto(booking);
	}

	@Override
	public List<BookingDto> getBookingsByClient(Long clienteId) {
		return bookingRepository.findByClienteId(clienteId).stream().map(this::convertToDto)
				.collect(Collectors.toList());
	}

	@Override
	public List<BookingDto> getBookingsByProfessional(Long profesionalId) {
		return bookingRepository.findByProfesionalId(profesionalId).stream().map(this::convertToDto)
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public BookingDto createBooking(BookingDto bookingDto) {
		Booking booking = new Booking();
		booking.setClienteId(bookingDto.getClienteId());
		booking.setServicioId(bookingDto.getServicioId());
		if (bookingDto.getFecha() == null || bookingDto.getFecha().isBefore(LocalDate.now())) {
			throw new IllegalArgumentException("La fecha de la reserva no es válida");
		}
		booking.setFecha(bookingDto.getFecha());
		booking.setEstado(Booking.BookingStatus.pendiente);

		Booking savedBooking = bookingRepository.save(booking);
		return convertToDto(savedBooking);
	}

	@Override
	@Transactional
	public BookingDto updateBookingStatus(Long id, Booking.BookingStatus status) {
		Booking booking = bookingRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Reserva no encontrada con ID: " + id));

		booking.setEstado(status);
		Booking updatedBooking = bookingRepository.save(booking);
		return convertToDto(updatedBooking);
	}

	@Override
	public List<BookingDto> getBookingsByStatus(Booking.BookingStatus status) {
		return bookingRepository.findByEstado(status).stream().map(this::convertToDto).collect(Collectors.toList());
	}

	private BookingDto convertToDto(Booking booking) {
		BookingDto dto = new BookingDto();
		dto.setId(booking.getId());
		dto.setClienteId(booking.getClienteId());
		dto.setServicioId(booking.getServicioId());
		dto.setFecha(booking.getFecha());
		dto.setEstado(booking.getEstado());
		dto.setValoracion(booking.getValoracion());
		dto.setComentario(booking.getComentario());

		if (booking.getCliente() != null) {
			dto.setClienteNombre(booking.getCliente().getNombre());
		}
		if (booking.getServicio() != null) {
			dto.setServicioTitulo(booking.getServicio().getTitulo());
		}

		return dto;
	}
}