package dat;

import dat.config.HibernateConfig;
import dat.daos.MovieDAO;
import dat.dto.*;
import dat.services.FetchDanishMovies;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class Main {
    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static final MovieDAO MOVIE_DAO = MovieDAO.getInstance(emf);

    public static void main(String[] args) {
        List<GenreDTO> genres = getGenres();
        System.out.println("Fetched Genres: " + genres);

        List<Long> movieIds = FetchDanishMovies.fetchMovieIds();
        processMovies(movieIds, genres);
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
