package dat;

import dat.config.HibernateConfig;
import dat.daos.ActorDAO;
import dat.daos.DirectorDAO;
import dat.daos.GenreDAO;
import dat.daos.MovieDAO;
import jakarta.persistence.EntityManagerFactory;


public class Main {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static final MovieDAO MOVIE_DAO = MovieDAO.getInstance(emf);
    private static final GenreDAO GENRE_DAO = GenreDAO.getInstance(emf);
    private static final DirectorDAO DIRECTOR_DAO = DirectorDAO.getInstance(emf);
    private static final ActorDAO ACTOR_DAO = ActorDAO.getInstance(emf);

    public static void main(String[] args) {

        //TODO adds movies, genres, actors and director to the database
//        Set<Long> movieIds = FetchDanishMovies.fetchMovieIds();
//        processMovies(movieIds);

        //---------------------------------------------------------------

//        ACTOR_DAO.readAll().forEach(System.out::println);
//        DIRECTOR_DAO.readAll().forEach(System.out::println);
//        printAllMovies();
//        GENRE_DAO.readAll().forEach(System.out::println);
//        GENRE_DAO.getMoviesByGenre("romance").forEach(System.out::println);
//        searchMovieName("Bob");
//        averageRating();
//        lowestRatedMovie();
//        topRatedMovie();
//        mostPopularMovie();

        //---------------------------------------------------------------
    }
}
