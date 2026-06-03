package com.moviebooking.controller;

import com.moviebooking.model.Booking;
import com.moviebooking.service.BookingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@Tag(name = "Bookings", description = "Create and retrieve movie ticket bookings")
public class BookingController {

    private final BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @Operation(
        summary = "Create a new booking",
        description = "Books movie tickets for a customer. " +
                      "The backend automatically calculates totalPrice and sets movieName from the movie ID. " +
                      "If bookingDate is not provided, today's date is used."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Booking created successfully",
            content = @Content(schema = @Schema(implementation = Booking.class),
                examples = @ExampleObject(value = """
                    {
                      "id": 1,
                      "movieId": 2,
                      "movieName": "Jailer",
                      "customerName": "Praveen Kumar",
                      "customerEmail": "praveen@example.com",
                      "customerPhone": "9876543210",
                      "numberOfSeats": 2,
                      "totalPrice": 500.0,
                      "bookingDate": "2024-06-10"
                    }"""))),
        @ApiResponse(responseCode = "404", description = "Movie not found for the given movieId",
            content = @Content(examples = @ExampleObject(value = """
                    {
                      "status": 404,
                      "error": "Not Found",
                      "message": "Movie not found with id: 99"
                    }""")))
    })
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Booking createBooking(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Booking details — movieId, customer info, seats and date",
                required = true,
                content = @Content(schema = @Schema(implementation = Booking.class),
                    examples = @ExampleObject(value = """
                        {
                          "movieId": 2,
                          "customerName": "Praveen Kumar",
                          "customerEmail": "praveen@example.com",
                          "customerPhone": "9876543210",
                          "numberOfSeats": 2,
                          "bookingDate": "2024-06-10"
                        }""")))
            @RequestBody Booking booking) {
        return bookingService.createBooking(booking);
    }

    @Operation(
        summary = "Get bookings by customer email",
        description = "Returns all bookings for a specific customer email (case-insensitive), " +
                      "sorted by booking date descending (newest first). " +
                      "If no email is provided, returns all bookings in the system."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Bookings returned successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Booking.class)),
                examples = @ExampleObject(value = """
                    [
                      {
                        "id": 2,
                        "movieId": 3,
                        "movieName": "Vikram",
                        "customerName": "Praveen Kumar",
                        "customerEmail": "praveen@example.com",
                        "customerPhone": "9876543210",
                        "numberOfSeats": 3,
                        "totalPrice": 900.0,
                        "bookingDate": "2024-06-15"
                      },
                      {
                        "id": 1,
                        "movieId": 2,
                        "movieName": "Jailer",
                        "customerName": "Praveen Kumar",
                        "customerEmail": "praveen@example.com",
                        "customerPhone": "9876543210",
                        "numberOfSeats": 2,
                        "totalPrice": 500.0,
                        "bookingDate": "2024-06-10"
                      }
                    ]""")))
    })
    @GetMapping
    public List<Booking> getBookingsByEmail(
            @Parameter(description = "Customer email address to filter bookings", example = "praveen@example.com")
            @RequestParam(required = false) String email) {
        if (email == null || email.isEmpty()) {
            return bookingService.getAllBookings();
        }
        return bookingService.getBookingsByEmail(email);
    }
}
