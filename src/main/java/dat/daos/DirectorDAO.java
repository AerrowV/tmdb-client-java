package dat.daos;

import dat.dto.DirectorDTO;
import dat.entities.Director;
import dat.exceptions.ApiException;
import dat.services.DTOMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class DirectorDAO implements IDAO<Director, Long> {
    private static EntityManagerFactory emf;
    private static DirectorDAO instance = null;

    public DirectorDAO() {
    }

    public static DirectorDAO getInstance(EntityManagerFactory _emf) {
        if (emf == null) {
            emf = _emf;
            instance = new DirectorDAO();
        }
        return instance;
    }

    public Director saveFromDTO(DirectorDTO directorDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                em.getTransaction().begin();

                Director director = em.find(Director.class, directorDTO.getId());
                if (director != null) {
                    System.out.println("Director already exists: " + director.getName());
                    em.getTransaction().commit();
                    return director;
                }

                director = DTOMapper.directorToEntity(directorDTO);
                em.persist(director);
                em.getTransaction().commit();
                System.out.println("New director saved: " + director.getName());

                return director;
            } catch (Exception e) {
                em.getTransaction().rollback();
                System.err.println("Error while saving director: " + e.getMessage());
                throw new ApiException(401, "An error occurred while saving the director: " + e.getMessage());
            }
        }
    }

    @Override
    public Director save(Director director) {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                em.getTransaction().begin();
                em.persist(director);
                em.getTransaction().commit();
                return director;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new ApiException(401, "An error occurred while saving the director");
            }
        }
    }

    @Override
    public Director read(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Director.class, id);
        }
    }

    @Override
    public List<Director> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT d FROM Director d", Director.class).getResultList();
        }
    }

    @Override
    public Director update(Director director) {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                em.getTransaction().begin();
                Director updatedDirector = em.merge(director);
                em.getTransaction().commit();
                return updatedDirector;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new ApiException(401, "An error occurred while updating the director");
            }
        }
    }

    @Override
    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                em.getTransaction().begin();
                Director director = em.find(Director.class, id);
                if (director != null) {
                    em.remove(director);
                }
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new ApiException(401, "An error occurred while deleting the director");
            }
        }
    }
}