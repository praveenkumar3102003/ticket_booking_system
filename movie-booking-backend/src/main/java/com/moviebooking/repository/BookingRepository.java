package com.moviebooking.repository;

import com.moviebooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b WHERE LOWER(b.customerEmail) = LOWER(:email) ORDER BY b.bookingDate DESC")
    List<Booking> findByCustomerEmailIgnoreCaseOrderByBookingDateDesc(@Param("email") String email);
}
