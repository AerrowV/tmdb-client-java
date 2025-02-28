package dat;

import dat.config.HibernateConfig;
import dat.daos.ActorDAO;
import dat.daos.DirectorDAO;
import dat.daos.GenreDAO;
import dat.daos.MovieDAO;
import dat.dto.*;
import dat.entities.Movie;
import dat.services.FetchDanishMovies;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

import static dat.services.FetchDanishMovies.printAllMovieDetails;

public class Main {
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static final MovieDAO MOVIE_DAO = MovieDAO.getInstance(emf);
    private static final ActorDAO ACTOR_DAO = ActorDAO.getInstance(emf);
    private static final DirectorDAO DIRECTOR_DAO = DirectorDAO.getInstance(emf);
    private static final GenreDAO GENRE_DAO = GenreDAO.getInstance(emf);

    public static void main(String[] args) {
        List<GenreDTO> genres = getGenres();
        System.out.println("Fetched Genres: " + genres);

        List<Long> movieIds = FetchDanishMovies.fetchMovieIds();
        processMovies(movieIds, genres);

//        printAllMovies();
//        ACTOR_DAO.readAll().forEach(System.out::println);
//        DIRECTOR_DAO.readAll().forEach(System.out::println);
//        MOVIE_DAO.getAllMoviesWithDetails().forEach(System.out::println);
//        GENRE_DAO.readAll().forEach(System.out::println);

//        GENRE_DAO.getMoviesByGenre("drama").forEach(System.out::println);


    }

    private static void printAllMovies() {
        List<Movie> movies = MOVIE_DAO.readAll();
        System.out.println("--Movie List--");
        movies.forEach(movie -> System.out.println("Title: " + movie.getTitle() + "\n" + "Description: " + movie.getOverview() + "\n" + "Release date: " + movie.getReleaseDate() + "\n" + "Rating: " + movie.getRating() + "\n------------------------"));
    }

    private static List<GenreDTO> getGenres() {
        GenreResponseDTO genreResponse = FetchDanishMovies.fetchGenreDetails();
        return (genreResponse != null) ? genreResponse.getGenres() : List.of();
    }

    private static void processMovies(List<Long> movieIds, List<GenreDTO> genres) {
        for (Long movieId : movieIds) {
            saveMovie(movieId, genres);
        }
    }

    private static void saveMovie(Long movieId, List<GenreDTO> genres) {
        MovieDTO movieDTO = FetchDanishMovies.fetchMovieDetails(movieId);
        if (movieDTO == null) return;

        System.out.println("Fetching details for: " + movieDTO.getTitle());

        List<ActorDTO> actors = FetchDanishMovies.fetchActorDetails(movieId);
        List<DirectorDTO> directors = FetchDanishMovies.fetchDirectorDetails(movieId);

        System.out.println("Actors: " + actors);
        System.out.println("Directors: " + directors);

        MOVIE_DAO.saveMovieFromDTO(movieDTO, genres, directors, actors);
        System.out.println("Saved Movie: " + movieDTO.getTitle());
    }
}
