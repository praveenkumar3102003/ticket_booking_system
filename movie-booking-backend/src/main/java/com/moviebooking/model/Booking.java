package com.moviebooking.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "bookings")
@Schema(description = "Booking entity representing a movie ticket reservation")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique booking ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "ID of the movie being booked", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long movieId;

    @Schema(description = "Name of the movie (set automatically by backend)", example = "Jailer", accessMode = Schema.AccessMode.READ_ONLY)
    private String movieName;

    @Schema(description = "Full name of the customer", example = "Praveen Kumar", requiredMode = Schema.RequiredMode.REQUIRED)
    private String customerName;

    @Schema(description = "Email address of the customer", example = "praveen@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String customerEmail;

    @Schema(description = "10-digit phone number of the customer", example = "9876543210", requiredMode = Schema.RequiredMode.REQUIRED)
    private String customerPhone;

    @Schema(description = "Number of seats to book (1-10)", example = "2", requiredMode = Schema.RequiredMode.REQUIRED)
    private int numberOfSeats;

    @Schema(description = "Total price calculated by backend (price × seats)", example = "500.0", accessMode = Schema.AccessMode.READ_ONLY)
    private double totalPrice;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "Date selected for watching the movie (yyyy-MM-dd). Defaults to today if not provided.", example = "2024-06-10")
    private LocalDate bookingDate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getMovieId() { return movieId; }
    public void setMovieId(Long movieId) { this.movieId = movieId; }

    public String getMovieName() { return movieName; }
    public void setMovieName(String movieName) { this.movieName = movieName; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }

    public String getCustomerPhone() { return customerPhone; }
    public void setCustomerPhone(String customerPhone) { this.customerPhone = customerPhone; }

    public int getNumberOfSeats() { return numberOfSeats; }
    public void setNumberOfSeats(int numberOfSeats) { this.numberOfSeats = numberOfSeats; }

    public double getTotalPrice() { return totalPrice; }
    public void setTotalPrice(double totalPrice) { this.totalPrice = totalPrice; }

    public LocalDate getBookingDate() { return bookingDate; }
    public void setBookingDate(LocalDate bookingDate) { this.bookingDate = bookingDate; }
}
