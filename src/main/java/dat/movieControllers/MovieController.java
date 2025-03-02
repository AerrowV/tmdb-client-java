package dat.movieControllers;

import dat.config.HibernateConfig;
import dat.daos.MovieDAO;
import dat.entities.Movie;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class MovieController {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    private static final MovieDAO MOVIE_DAO = MovieDAO.getInstance(emf);


    public static void averageRating() {
        Double average = MOVIE_DAO.getAverageForAllMovies();
        System.out.println("Average Rating: " + average);
    }

    public static void lowestRatedMovie() {
        List<Movie> lowestRated = MOVIE_DAO.getTenLowestRatedMovies();
        for (Movie lowestMovie : lowestRated){
            System.out.println(lowestMovie.getTitle() + "- Rating: " + lowestMovie.getRating());
        }
    }

    public static void topRatedMovie() {
        List<Movie> topRated = MOVIE_DAO.getTenHighestRatedMovies();
        for (Movie topMovie : topRated){
            System.out.println(topMovie.getTitle() + "- Rating: " + topMovie.getRating());
        }
    }

    public static void mostPopularMovie() {
        List<Movie> mostPopular = MOVIE_DAO.getMostPopularMovies();
        for (Movie popularMovie : mostPopular){
            System.out.println(popularMovie.getTitle() + "- Popularity: " + popularMovie.getPopularity());
        }
    }

}


