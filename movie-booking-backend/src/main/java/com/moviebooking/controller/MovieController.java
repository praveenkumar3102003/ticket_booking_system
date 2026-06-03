package com.moviebooking.controller;

import com.moviebooking.model.Movie;
import com.moviebooking.service.MovieService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/movies")
@Tag(name = "Movies", description = "Browse and retrieve movie details")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @Operation(
        summary = "Get all movies",
        description = "Returns a list of all available movies in the system."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "List of movies returned successfully",
            content = @Content(array = @ArraySchema(schema = @Schema(implementation = Movie.class)),
                examples = @ExampleObject(value = """
                    [
                      {
                        "id": 1,
                        "name": "Leo",
                        "price": 200.0,
                        "language": "Tamil",
                        "genre": "Action",
                        "releaseDate": "2024-01-01"
                      },
                      {
                        "id": 2,
                        "name": "Jailer",
                        "price": 250.0,
                        "language": "Tamil",
                        "genre": "Drama",
                        "releaseDate": "2024-02-01"
                      }
                    ]""")))
    })
    @GetMapping
    public List<Movie> getAllMovies() {
        return movieService.getAllMovies();
    }

    @Operation(
        summary = "Get movie by ID",
        description = "Returns full details of a single movie by its ID."
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Movie found",
            content = @Content(schema = @Schema(implementation = Movie.class),
                examples = @ExampleObject(value = """
                    {
                      "id": 1,
                      "name": "Leo",
                      "price": 200.0,
                      "language": "Tamil",
                      "genre": "Action",
                      "releaseDate": "2024-01-01"
                    }"""))),
        @ApiResponse(responseCode = "404", description = "Movie not found",
            content = @Content(examples = @ExampleObject(value = """
                    {
                      "timestamp": "2024-06-03T10:00:00",
                      "status": 404,
                      "error": "Not Found",
                      "message": "Movie not found with id: 99"
                    }""")))
    })
    @GetMapping("/{id}")
    public Movie getMovieById(
            @Parameter(description = "ID of the movie to retrieve", example = "1")
            @PathVariable Long id) {
        return movieService.getMovieById(id);
    }
}
