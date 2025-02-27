package dat;

import dat.config.HibernateConfig;
import dat.daos.ActorDAO;
import dat.daos.MovieDAO;
import dat.dto.MovieDTO;
import dat.services.FetchDanishMovies;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class Main {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static final MovieDAO MOVIE_DAO = MovieDAO.getInstance(emf);
    private static final MovieDAO ACTOR_DAO = MovieDAO.getInstance(emf);

    public static void main(String[] args) {

        List<Long> movieIds = FetchDanishMovies.fetchMovieIds();

//        for (Long movieId : movieIds) {
//            MovieDTO movieDTO = FetchDanishMovies.fetchMovieDetails(movieId);
//            if (movieDTO != null) {
//                MOVIE_DAO.saveMovieFromDTO(movieDTO);
//                System.out.println("Saved Movie: " + movieDTO.getTitle());
//            }
//        }
    }
}