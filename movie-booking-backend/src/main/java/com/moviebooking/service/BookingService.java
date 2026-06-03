package com.moviebooking.service;

import com.moviebooking.model.Booking;
import com.moviebooking.model.Movie;
import com.moviebooking.repository.BookingRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final MovieService movieService;

    public BookingService(BookingRepository bookingRepository, MovieService movieService) {
        this.bookingRepository = bookingRepository;
        this.movieService = movieService;
    }

    @Transactional
    public Booking createBooking(Booking booking) {
        Movie movie = movieService.getMovieById(booking.getMovieId());
        booking.setMovieName(movie.getName());
        booking.setTotalPrice(movie.getPrice() * booking.getNumberOfSeats());
        if (booking.getBookingDate() == null) {
            booking.setBookingDate(LocalDate.now());
        }
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByEmail(String email) {
        return bookingRepository.findByCustomerEmailIgnoreCaseOrderByBookingDateDesc(email.trim());
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }
}
