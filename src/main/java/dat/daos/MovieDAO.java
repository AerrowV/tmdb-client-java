package dat.daos;

import dat.config.HibernateConfig;
import dat.entities.Movie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class MovieDAO implements IDAO<Movie, Integer>{
    private EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();


    @Override
    public Movie create(Movie movie) {
            EntityManager em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(movie);
            em.getTransaction().commit();
            em.close();
            return movie;

    }

    @Override
    public Movie read(Integer integer) {
        return null;
    }

    @Override
    public List<Movie> readAll() {
        return List.of();
    }

    @Override
    public Movie update(Movie movie) {
        return null;
    }

    @Override
    public void delete(Integer integer) {

    }
}
