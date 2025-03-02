package dat.daos;

import dat.dto.ActorDTO;
import dat.dto.DirectorDTO;
import dat.dto.GenreDTO;
import dat.dto.MovieDTO;
import dat.entities.Actor;
import dat.entities.Director;
import dat.entities.Genre;
import dat.entities.Movie;
import dat.exceptions.ApiException;
import dat.services.DTOMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MovieDAO implements IDAO<Movie, Integer> {
    private static EntityManagerFactory emf;
    private static MovieDAO instance = null;

    public MovieDAO() {
    }

    public static MovieDAO getInstance(EntityManagerFactory _emf) {
        if (emf == null) {
            emf = _emf;
            instance = new MovieDAO();
        }
        return instance;
    }

    public Movie saveMovieFromDTO(MovieDTO movieDTO, Set<GenreDTO> genreDTOs, List<DirectorDTO> directorDTOs, List<ActorDTO> actorDTOs) {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                em.getTransaction().begin();
                System.out.println("Saving movie: " + movieDTO.getTitle());

                GenreDAO genreDAO = GenreDAO.getInstance(emf);
                Set<Genre> genres = new HashSet<>();
                for (GenreDTO genreDTO : genreDTOs) {
                    genres.add(genreDAO.saveFromDTO(genreDTO));
                }


                DirectorDAO directorDAO = DirectorDAO.getInstance(emf);
                Director director = null;
                if (!directorDTOs.isEmpty()) {
                    director = directorDAO.saveFromDTO(directorDTOs.get(0));
                }

                ActorDAO actorDAO = ActorDAO.getInstance(emf);
                Set<Actor> actors = new HashSet<>();
                for (ActorDTO actorDTO : actorDTOs) {
                    actors.add(actorDAO.saveActorFromDTO(actorDTO));
                }

                Movie movie = DTOMapper.movieToEntity(movieDTO, genres, director, actors);
                em.merge(movie);
                em.getTransaction().commit();

                System.out.println("Movie saved successfully: " + movieDTO.getTitle());
                return movie;
            } catch (Exception e) {
                em.getTransaction().rollback();
                System.err.println("Error while saving movie: " + e.getMessage());
                throw new ApiException(401, "An error occurred while saving the movie: " + e.getMessage());
            }
        }
    }

    public List<Movie> readMovie(String movieName) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery(
                            "SELECT DISTINCT m FROM Movie m " +
                                    "LEFT JOIN FETCH m.genres " +
                                    "LEFT JOIN FETCH m.director " +
                                    "LEFT JOIN FETCH m.actors " +
                                    "WHERE m.title = :movieName", Movie.class)
                    .setParameter("movieName", movieName)
                    .getResultList();
        }
    }

    public double getAverageForAllMovies() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT AVG(m.rating) FROM Movie m", Double.class).getSingleResult();
        }
    }

    public List<Movie> getTenLowestRatedMovies() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT m FROM Movie m ORDER BY m.rating ASC", Movie.class)
                    .setMaxResults(10)
                    .getResultList();
        }
    }

    public List<Movie> getTenHighestRatedMovies() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT m FROM Movie m ORDER BY m.rating DESC", Movie.class)
                    .setMaxResults(10)
                    .getResultList();
        }
    }

    public List<Movie> getMostPopularMovies() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT m FROM Movie m ORDER BY m.popularity DESC", Movie.class)
                    .setMaxResults(10)
                    .getResultList();
        }
    }

    @Override
    public Movie create(Movie movie) {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                em.getTransaction().begin();
                em.persist(movie);
                em.getTransaction().commit();
                return movie;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new ApiException(401, "An error occurred while saving the movie");
            }
        }
    }

    @Override
    public Movie read(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Movie.class, id);
        }
    }

    @Override
    public List<Movie> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT m FROM Movie m", Movie.class).getResultList();
        }
    }

    @Override
    public Movie update(Movie movie) {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                em.getTransaction().begin();
                Movie updatedMovie = em.merge(movie);
                em.getTransaction().commit();
                return updatedMovie;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new ApiException(401, "An error occurred while updating the movie");
            }
        }
    }

    @Override
    public void delete(Integer id) {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                em.getTransaction().begin();
                Movie movie = em.find(Movie.class, id);
                if (movie != null) {
                    em.remove(movie);
                }
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new ApiException(401, "An error occurred while deleting the movie");
            }
        }
    }
}
