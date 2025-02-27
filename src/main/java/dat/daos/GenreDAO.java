package dat.daos;

import dat.entities.Genre;
import dat.dto.GenreDTO;
import dat.services.DTOMapper;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import java.util.List;

public class GenreDAO implements IDAO<Genre, Long> {
    private static EntityManagerFactory emf;
    private static GenreDAO instance = null;

    public GenreDAO() {}

    public static GenreDAO getInstance(EntityManagerFactory _emf) {
        if (emf == null) {
            emf = _emf;
            instance = new GenreDAO();
        }
        return instance;
    }

    @Override
    public Genre save(Genre genre) {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                em.getTransaction().begin();
                em.persist(genre);
                em.getTransaction().commit();
                return genre;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new ApiException(401, "An error occurred while saving the genre");
            }
        }
    }

    // Method to save a Genre from a DTO
    public Genre saveFromDTO(GenreDTO genreDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                em.getTransaction().begin();
                // Use DTOMapper to convert DTO to Entity
                Genre genre = DTOMapper.toEntity(genreDTO);
                em.persist(genre);
                em.getTransaction().commit();
                return genre;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new ApiException(401, "An error occurred while saving the genre");
            }
        }
    }

    @Override
    public Genre read(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            return em.find(Genre.class, id);
        }
    }

    @Override
    public List<Genre> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            return em.createQuery("SELECT g FROM Genre g", Genre.class).getResultList();
        }
    }

    @Override
    public Genre update(Genre genre) {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                em.getTransaction().begin();
                Genre updatedGenre = em.merge(genre);
                em.getTransaction().commit();
                return updatedGenre;
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new ApiException(401, "An error occurred while updating the genre");
            }
        }
    }

    @Override
    public void delete(Long id) {
        try (EntityManager em = emf.createEntityManager()) {
            try {
                em.getTransaction().begin();
                Genre genre = em.find(Genre.class, id);
                if (genre != null) {
                    em.remove(genre);
                }
                em.getTransaction().commit();
            } catch (Exception e) {
                em.getTransaction().rollback();
                throw new ApiException(401, "An error occurred while deleting the genre");
            }
        }
    }
}
