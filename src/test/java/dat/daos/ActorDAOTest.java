package dat.daos;

import dat.config.HibernateConfig;
import dat.dto.ActorDTO;
import dat.entities.Actor;
import dat.exceptions.ApiException;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ActorDAOTest {

    private static ActorDAO actorDAO;
    private static EntityManagerFactory emf;

    @BeforeAll
    static void setUpAll() {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        actorDAO = ActorDAO.getInstance(emf);
    }

    @AfterAll
    static void tearDownAll() {
        if (emf != null) {
            emf.close();
        }
    }

    @BeforeEach
    void setUp() {
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Actor").executeUpdate();
            em.getTransaction().commit();
        }
    }

    @Test
    void testCreateActor() {
        Actor actor = new Actor();
        actor.setName("John Doe");

        Actor createdActor = actorDAO.create(actor);
        assertNotNull(createdActor.getId());
        assertEquals("John Doe", createdActor.getName());
    }

    @Test
    void testReadActor() {
        Actor actor = new Actor();
        actor.setName("Jane Doe");

        Actor createdActor = actorDAO.create(actor);
        Actor foundActor = actorDAO.read(createdActor.getId());

        assertNotNull(foundActor);
        assertEquals("Jane Doe", foundActor.getName());
    }

    @Test
    void testReadAllActors() {
        Actor actor1 = new Actor();
        actor1.setName("Actor 1");

        Actor actor2 = new Actor();
        actor2.setName("Actor 2");

        actorDAO.create(actor1);
        actorDAO.create(actor2);

        List<Actor> actors = actorDAO.readAll();
        assertEquals(2, actors.size());
    }

    @Test
    void testUpdateActor() {
        Actor actor = new Actor();
        actor.setName("Old Name");

        Actor createdActor = actorDAO.create(actor);
        createdActor.setName("New Name");

        Actor updatedActor = actorDAO.update(createdActor);

        assertEquals("New Name", updatedActor.getName());
    }

    @Test
    void testDeleteActor() {
        Actor actor = new Actor();
        actor.setName("Actor to Delete");

        Actor createdActor = actorDAO.create(actor);
        Long actorId = createdActor.getId();

        actorDAO.delete(actorId);
        Actor deletedActor = actorDAO.read(actorId);

        assertNull(deletedActor);
    }

    @Test
    void testSaveActorFromDTO() {
        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setId(1L);
        actorDTO.setName("DTO Actor");

        Actor savedActor = actorDAO.saveActorFromDTO(actorDTO);

        assertNotNull(savedActor.getId());
        assertEquals("DTO Actor", savedActor.getName());
    }

    @Test
    void testSaveActorFromDTO_WhenActorExists() {
        Actor actor = new Actor();
        actor.setName("Existing Actor");
        actorDAO.create(actor);

        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setId(actor.getId());
        actorDTO.setName("Existing Actor");

        Actor savedActor = actorDAO.saveActorFromDTO(actorDTO);

        assertEquals(actor.getId(), savedActor.getId());
        assertEquals("Existing Actor", savedActor.getName());
    }

    @Test
    void testSaveActorFromDTO_ThrowsApiException() {
        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setId(1L);
        actorDTO.setName(null);

        assertThrows(ApiException.class, () -> actorDAO.saveActorFromDTO(actorDTO));
    }
}