package dat.controllers;

import dat.config.HibernateConfig;
import dat.daos.MovieDAO;
import dat.dto.ActorDTO;
import dat.dto.DirectorDTO;
import dat.dto.GenreDTO;
import dat.dto.MovieDTO;
import dat.entities.Movie;
import dat.services.FetchDanishMovies;
import jakarta.persistence.EntityManagerFactory;

import java.util.Collection;
import java.util.List;
import java.util.Set;


public class MovieController {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static final MovieDAO MOVIE_DAO = MovieDAO.getInstance(emf);

    public static void saveMovie(Long movieId) {
        MovieDTO movieDTO = FetchDanishMovies.fetchMovieDetails(movieId);
        if (movieDTO == null) return;

        System.out.println("Fetching details for: " + movieDTO.getTitle());

        Set<ActorDTO> actors = FetchDanishMovies.fetchActorDetails(movieId);
        List<DirectorDTO> directors = FetchDanishMovies.fetchDirectorDetails(movieId);
        List<GenreDTO> genres = movieDTO.getGenres();

        System.out.println("Actors: " + actors);
        System.out.println("Directors: " + directors);
        System.out.println("Genres: " + genres);

        MOVIE_DAO.saveMovieFromDTO(movieDTO, genres, directors, actors);
        System.out.println("Saved Movie: " + movieDTO.getTitle());
    }


    public static void printAllMovies() {
        List<Movie> movies = MOVIE_DAO.readAll();
        System.out.println("--Movie List--");
        movies.forEach(movie -> System.out.println("Title: " + movie.getTitle() + "\n" + "Description: " + movie.getOverview() + "\n" + "Release date: " + movie.getReleaseDate() + "\n" + "Rating: " + movie.getRating() + "\n------------------------"));
    }

    public static void processMovies(Collection<Long> movieIds) {
        for (Long movieId : movieIds) {
            saveMovie(movieId);
        }
    }

    public static void searchMovieName(String movieName) {

        List<Movie> movie = MOVIE_DAO.readMovie(movieName);

        for (Movie selectedMovie : movie) {
            System.out.println("Title: " + selectedMovie.getTitle());
            System.out.println("Release Date: " + selectedMovie.getReleaseDate());
            System.out.println("Rating: " + selectedMovie.getRating());
            System.out.println("Popularity: " + selectedMovie.getPopularity());
            System.out.println("Overview: " + selectedMovie.getOverview());
        }
    }

    public static void averageRating() {
        Double average = MOVIE_DAO.getAverageRating();
        System.out.println("Average Rating: " + average);
    }

    public static void lowestRatedMovie() {
        List<Movie> lowestRated = MOVIE_DAO.getLowestRatedMovies();
        for (Movie lowestMovie : lowestRated) {
            System.out.println(lowestMovie.getTitle() + "- Rating: " + lowestMovie.getRating());
        }
    }

    public static void topRatedMovie() {
        List<Movie> topRated = MOVIE_DAO.getTopRatedMovies();
        for (Movie topMovie : topRated) {
            System.out.println(topMovie.getTitle() + "- Rating: " + topMovie.getRating());
        }
    }

    public static void mostPopularMovie() {
        List<Movie> mostPopular = MOVIE_DAO.getMostPopularMovies();
        for (Movie popularMovie : mostPopular) {
            System.out.println(popularMovie.getTitle() + "- Popularity: " + popularMovie.getPopularity());
        }
    }
}
