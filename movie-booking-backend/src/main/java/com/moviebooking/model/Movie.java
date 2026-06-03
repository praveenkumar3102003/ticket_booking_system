package com.moviebooking.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

@Entity
@Table(name = "movies")
@Schema(description = "Movie entity representing a film available for booking")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "Unique movie ID", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Title of the movie", example = "Leo")
    private String name;

    @Schema(description = "Ticket price per seat in INR", example = "200.0")
    private double price;

    @Schema(description = "Language of the movie", example = "Tamil")
    private String language;

    @Schema(description = "Genre of the movie", example = "Action")
    private String genre;

    @Schema(description = "Release date of the movie (yyyy-MM-dd)", example = "2024-01-01")
    private String releaseDate;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }

    public String getReleaseDate() { return releaseDate; }
    public void setReleaseDate(String releaseDate) { this.releaseDate = releaseDate; }
}
