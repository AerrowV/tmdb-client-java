package dat;

import dat.config.HibernateConfig;
import dat.daos.MovieDAO;
import dat.dto.*;
import dat.entities.Genre;
import dat.entities.Movie;
import dat.movieControllers.MovieController;
import dat.services.DTOMapper;
import dat.services.FetchDanishMovies;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static dat.movieControllers.MovieController.*;

public class Main {
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static final MovieDAO MOVIE_DAO = MovieDAO.getInstance(emf);

    public static void main(String[] args) {
//        averageRating();
//        lowestRatedMovie();
//        topRatedMovie();
//        mostPopularMovie();

        List<Long> movieIds = FetchDanishMovies.fetchMovieIds();

        processMovies(movieIds);


        String movieName = "A Copenhagen Love Story";
       // searchMovie(movieName);



    }

    private static void searchMovie(String movieName) {
        MovieDAO movieDAO = new MovieDAO();
        List<Movie> movie = movieDAO.readMovie(movieName);

        for (Movie selectedMovie : movie) {
            System.out.println("Title: " + selectedMovie.getTitle());
            System.out.println("Release Date: " + selectedMovie.getReleaseDate());
            System.out.println("Rating: " + selectedMovie.getRating());
            System.out.println("Popularity: " + selectedMovie.getPopularity());
            System.out.println("Overview: " + selectedMovie.getOverview());

            // Print Director
            if (selectedMovie.getDirector() != null) {
                System.out.println("Director: " + selectedMovie.getDirector().getName());
            }

            // Print Genres
            System.out.print("Genres: ");
            selectedMovie.getGenres().forEach(genre -> System.out.print(genre.getName() + ", "));
            System.out.println();

            // Print Actors
            System.out.print("Actors: ");
            selectedMovie.getActors().forEach(actor -> System.out.print(actor.getName() + ", "));
            System.out.println("\n-----------------------------");
        }
    }

    private static void processMovies(List<Long> movieIds) {
        for (Long movieId : movieIds) {
            saveMovie(movieId);
        }
    }

    private static void saveMovie(Long movieId) {
        MovieDTO movieDTO = FetchDanishMovies.fetchMovieDetails(movieId);
        if (movieDTO == null) return;

        System.out.println("Fetching details for: " + movieDTO.getTitle());

        List<ActorDTO> actors = FetchDanishMovies.fetchActorDetails(movieId);
        List<DirectorDTO> directors = FetchDanishMovies.fetchDirectorDetails(movieId);
        Set<GenreDTO> genres = movieDTO.getGenres();


        System.out.println("Actors: " + actors);
        System.out.println("Directors: " + directors);


        MOVIE_DAO.saveMovieFromDTO(movieDTO, genres, directors, actors);
        System.out.println("Saved Movie: " + movieDTO.getTitle());
    }
}
