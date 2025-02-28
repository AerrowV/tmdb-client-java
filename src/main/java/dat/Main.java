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
import java.util.Set;

import static dat.controllers.MovieController.printAllMovies;
import static dat.controllers.MovieController.processMovies;


public class Main {
    public static void main(String[] args) {

        //TODO adds movies, genres, actors and director to the database
//        Set<Long> movieIds = FetchDanishMovies.fetchMovieIds();
//        processMovies(movieIds);

        //---------------------------------------------------------------

//        ACTOR_DAO.readAll().forEach(System.out::println);
//        DIRECTOR_DAO.readAll().forEach(System.out::println);
//        printAllMovies();
//        GENRE_DAO.readAll().forEach(System.out::println);
//        GENRE_DAO.getMoviesByGenre("western").forEach(System.out::println);

    }
}
