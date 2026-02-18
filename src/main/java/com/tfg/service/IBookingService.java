package com.tfg.service;

import java.util.List;

import com.tfg.dto.BookingDto;
import com.tfg.model.Booking;

public interface IBookingService {
	List<BookingDto> getAllBookings();

	BookingDto getBookingById(Long id);

	List<BookingDto> getBookingsByClient(Long clienteId);

	List<BookingDto> getBookingsByProfessional(Long profesionalId);

	BookingDto createBooking(BookingDto bookingDto);

	BookingDto updateBookingStatus(Long id, Booking.BookingStatus status);

	List<BookingDto> getBookingsByStatus(Booking.BookingStatus status);
}