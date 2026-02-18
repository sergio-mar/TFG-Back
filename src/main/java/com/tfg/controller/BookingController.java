package com.tfg.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tfg.dto.BookingDto;
import com.tfg.model.Booking;
import com.tfg.service.IBookingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/bookings")
@RequiredArgsConstructor
public class BookingController {

	private final IBookingService bookingService;

	@GetMapping
	public ResponseEntity<List<BookingDto>> getAllBookings() {
		return ResponseEntity.ok(bookingService.getAllBookings());
	}

	@GetMapping("/{id}")
	public ResponseEntity<BookingDto> getBookingById(@PathVariable Long id) {
		return ResponseEntity.ok(bookingService.getBookingById(id));
	}

	@GetMapping("/client/{clienteId}")
	public ResponseEntity<List<BookingDto>> getBookingsByClient(@PathVariable Long clienteId) {
		return ResponseEntity.ok(bookingService.getBookingsByClient(clienteId));
	}

	@GetMapping("/professional/{profesionalId}")
	public ResponseEntity<List<BookingDto>> getBookingsByProfessional(@PathVariable Long profesionalId) {
		return ResponseEntity.ok(bookingService.getBookingsByProfessional(profesionalId));
	}

	@PostMapping
	public ResponseEntity<BookingDto> createBooking(@Valid @RequestBody BookingDto bookingDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(bookingService.createBooking(bookingDto));
	}

	@PutMapping("/{id}/status")
	public ResponseEntity<BookingDto> updateBookingStatus(@PathVariable Long id,
			@RequestBody Map<String, String> statusMap) {

		try {
			Booking.BookingStatus status = Booking.BookingStatus.valueOf(statusMap.get("status"));
			return ResponseEntity.ok(bookingService.updateBookingStatus(id, status));
		} catch (IllegalArgumentException e) {
			return ResponseEntity.badRequest().body(null);
		}
	}
}