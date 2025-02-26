package dat;

import dat.config.HibernateConfig;
import dat.daos.MovieDAO;
import dat.services.FetchDanishMovies;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

import static dat.services.FetchDanishMovies.printAllMovieDetails;

public class Main {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static final MovieDAO MOVIE_DAO = MovieDAO.getInstance(emf);
    public static void main(String[] args) {

        List<Long> movieIds = FetchDanishMovies.fetchMovieIds();

        printAllMovieDetails();

    }
}