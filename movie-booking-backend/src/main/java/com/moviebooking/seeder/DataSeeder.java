package com.moviebooking.seeder;

import com.moviebooking.model.Movie;
import com.moviebooking.repository.MovieRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final MovieRepository movieRepository;

    public DataSeeder(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void run(String... args) {
        // Only seed if table is empty — prevents duplicate inserts on every restart
        if (movieRepository.count() == 0) {
            List<Movie> movies = List.of(
                createMovie("Leo",              200, "Tamil", "Action", "2024-01-01"),
                createMovie("Jailer",           250, "Tamil", "Drama",  "2024-02-01"),
                createMovie("Vikram",           300, "Tamil", "Action", "2024-03-01"),
                createMovie("Ponniyin Selvan",  350, "Tamil", "Drama",  "2024-04-01"),
                createMovie("Don",              400, "Tamil", "Action", "2024-05-01"),
                createMovie("Sita Ramam",       450, "Tamil", "Drama",  "2024-06-01"),
                createMovie("Pathaan",          500, "Tamil", "Action", "2024-07-01"),
                createMovie("KGF 2",            550, "Tamil", "Action", "2024-08-01")
            );
            movieRepository.saveAll(movies);
            System.out.println("✅ Seeded " + movies.size() + " movies into the database.");
        } else {
            System.out.println("✅ Movies already exist in database, skipping seed.");
        }
    }

    private Movie createMovie(String name, double price, String language, String genre, String releaseDate) {
        Movie m = new Movie();
        m.setName(name);
        m.setPrice(price);
        m.setLanguage(language);
        m.setGenre(genre);
        m.setReleaseDate(releaseDate);
        return m;
    }
}
