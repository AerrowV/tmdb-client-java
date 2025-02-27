package dat.daos;

import dat.entities.Movie;
import dat.entities.Genre;
import dat.entities.Director;
import dat.entities.Actor;
import dat.dto.MovieDTO;
import dat.services.DTOMapper;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class MovieDAO implements IDAO<Movie, Integer> {
    private static EntityManagerFactory emf;
    private static MovieDAO instance = null;

    public MovieDAO() {}

    public static MovieDAO getInstance(EntityManagerFactory _emf) {
        if (emf == null) {
            emf = _emf;
            instance = new MovieDAO();
        }
        return instance;
    }

    public Movie saveMovieFromDTO(MovieDTO movieDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                em.getTransaction().begin();

                List<Genre> genres = em.createQuery(
                                "SELECT g FROM Genre g WHERE g.id IN :ids", Genre.class)
                        .setParameter("ids", movieDTO.getGenreIds())
                        .getResultList();

                Director director = em.find(Director.class, movieDTO.getId());

                List<Actor> actors = em.createQuery(
                                "SELECT a FROM Actor a WHERE a.id IN :ids", Actor.class)
                        .setParameter("ids", List.of(1, 2))
                        .getResultList();

                Movie movie = DTOMapper.toEntity(movieDTO, genres, director, actors);

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
    public Movie save(Movie movie) {
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
