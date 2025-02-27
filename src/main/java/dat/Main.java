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
        GenreResponseDTO genreResponse = FetchDanishMovies.fetchGenreDetails();
        List<GenreDTO> genres = (genreResponse != null) ? genreResponse.getGenres() : List.of();
        System.out.println("Fetched Genres: " + genres);

        List<Long> movieIds = FetchDanishMovies.fetchMovieIds();

        for (Long movieId : movieIds) {
            MovieDTO movieDTO = FetchDanishMovies.fetchMovieDetails(movieId);
            if (movieDTO != null) {
                System.out.println("Fetching details for: " + movieDTO.getTitle());

                List<ActorDTO> actors = FetchDanishMovies.fetchActorDetails(movieId);
                List<DirectorDTO> directors = FetchDanishMovies.fetchDirectorDetails(movieId);

                System.out.println("Actors: " + actors);
                System.out.println("Directors: " + directors);

                MOVIE_DAO.saveMovieFromDTO(movieDTO, genres, directors, actors);
                System.out.println("Saved Movie: " + movieDTO.getTitle());
            }
        }
    }
}
