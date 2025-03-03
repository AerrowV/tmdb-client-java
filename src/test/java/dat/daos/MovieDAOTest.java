package dat.daos;

import dat.config.HibernateConfig;
import dat.dto.ActorDTO;
import dat.dto.DirectorDTO;
import dat.dto.GenreDTO;
import dat.dto.MovieDTO;
import dat.entities.Actor;
import dat.entities.Director;
import dat.entities.Genre;
import dat.entities.Movie;
import dat.exceptions.ApiException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import jakarta.persistence.EntityManagerFactory;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
public class MovieDAOTest {

    @Container
    private static final PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15.3-alpine3.18")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    private static EntityManagerFactory emf;
    private static MovieDAO movieDAO;

    @BeforeAll
    static void setUp() {
        System.setProperty("DB_NAME", "testdb");
        System.setProperty("DB_USERNAME", "test");
        System.setProperty("DB_PASSWORD", "test");
        System.setProperty("CONNECTION_STR", "jdbc:postgresql://" + postgres.getHost() + ":" + postgres.getFirstMappedPort() + "/");

        emf = HibernateConfig.getEntityManagerFactoryForTest();
        movieDAO = MovieDAO.getInstance(emf);
    }

    @AfterAll
    static void tearDown() {
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    void testCreateMovie() {
        Movie movie = new Movie();
        movie.setTitle("Inception");
        movie.setRating(8.8);
        movie.setId(12345634L);

        Movie createdMovie = movieDAO.create(movie);
        assertNotNull(createdMovie.getId());
        assertEquals("Inception", createdMovie.getTitle());
        assertEquals(8.8, createdMovie.getRating());
    }

    @Test
    void testReadMovie() {
        Movie movie = new Movie();
        movie.setTitle("The Dark Knight");
        movie.setRating(9.0);
        movie.setId(12345634L);

        Movie createdMovie = movieDAO.create(movie);
        Movie foundMovie = movieDAO.read(createdMovie.getId());

        assertNotNull(foundMovie);
        assertEquals("The Dark Knight", foundMovie.getTitle());
        assertEquals(9.0f, foundMovie.getRating());
    }

    @Test
    void testReadAllMovies() {
        Movie movie1 = new Movie();
        movie1.setTitle("Movie 1");
        movie1.setRating(7.5);
        movie1.setId(12345634L);

        Movie movie2 = new Movie();
        movie2.setTitle("Movie 2");
        movie2.setRating(8.0);
        movie2.setId(123456234L);

        movieDAO.create(movie1);
        movieDAO.create(movie2);

        List<Movie> movies = movieDAO.readAll();
        assertEquals(2, movies.size());
    }

    @Test
    void testUpdateMovie() {
        Movie movie = new Movie();
        movie.setTitle("Old Title");
        movie.setRating(6.5);
        movie.setId(12345634L);

        Movie createdMovie = movieDAO.create(movie);
        createdMovie.setTitle("New Title");
        createdMovie.setRating(7.5);

        Movie updatedMovie = movieDAO.update(createdMovie);

        assertEquals("New Title", updatedMovie.getTitle());
        assertEquals(7.5, updatedMovie.getRating());
    }

    @Test
    void testDeleteMovie() {
        Movie movie = new Movie();
        movie.setTitle("Movie to Delete");
        movie.setRating(5.0);
        movie.setId(12345634L);

        Movie createdMovie = movieDAO.create(movie);
        Long movieId = createdMovie.getId();

        movieDAO.delete(movieId);
        Movie deletedMovie = movieDAO.read(movieId);

        assertNull(deletedMovie);
    }

    @Test
    void testSaveMovieFromDTO() {
        MovieDTO movieDTO = new MovieDTO();
        movieDTO.setTitle("Interstellar");
        movieDTO.setRating(8.6);

        GenreDTO genreDTO = new GenreDTO();
        genreDTO.setId(1L);
        genreDTO.setName("Sci-Fi");

        DirectorDTO directorDTO = new DirectorDTO();
        directorDTO.setId(1L);
        directorDTO.setName("Christopher Nolan");

        ActorDTO actorDTO = new ActorDTO();
        actorDTO.setId(1L);
        actorDTO.setName("Matthew McConaughey");

        Set<ActorDTO> actorDTOs = new HashSet<>();
        actorDTOs.add(actorDTO);

        Movie savedMovie = movieDAO.saveMovieFromDTO(movieDTO, List.of(genreDTO), List.of(directorDTO), actorDTOs);

        assertNotNull(savedMovie.getId());
        assertEquals("Interstellar", savedMovie.getTitle());
        assertEquals(8.6, savedMovie.getRating());
        assertEquals(1, savedMovie.getGenres().size());
        assertEquals("Christopher Nolan", savedMovie.getDirector().getName());
        assertEquals(1, savedMovie.getActor().size());
    }

    @Test
    void testGetAllMoviesWithDetails() {
        Movie movie = new Movie();
        movie.setTitle("The Matrix");
        movie.setRating(8.7);
        movie.setId(12345634L);

        movieDAO.create(movie);

        List<Movie> movies = movieDAO.getAllMoviesWithDetails();
        assertEquals(1, movies.size());
        assertEquals("The Matrix", movies.get(0).getTitle());
    }

    @Test
    void testReadMovieByName() {
        Movie movie = new Movie();
        movie.setTitle("The Matrix");
        movie.setRating(8.7);
        movie.setId(12345634L);

        movieDAO.create(movie);

        List<Movie> movies = movieDAO.readMovie("matrix");
        assertEquals(1, movies.size());
        assertEquals("The Matrix", movies.get(0).getTitle());
    }

    @Test
    void testGetAverageRating() {
        Movie movie1 = new Movie();
        movie1.setTitle("Movie 1");
        movie1.setRating(7.5);
        movie1.setId(12345634L);

        Movie movie2 = new Movie();
        movie2.setTitle("Movie 2");
        movie2.setRating(8.5);
        movie2.setId(123453634L);

        movieDAO.create(movie1);
        movieDAO.create(movie2);

        Double averageRating = movieDAO.getAverageRating();
        assertEquals(8.0, averageRating);
    }

    @Test
    void testGetTopRatedMovies() {
        Movie movie1 = new Movie();
        movie1.setTitle("Movie 1");
        movie1.setRating(9.0);

        Movie movie2 = new Movie();
        movie2.setTitle("Movie 2");
        movie2.setRating(8.5);

        movieDAO.create(movie1);
        movieDAO.create(movie2);

        List<Movie> topRatedMovies = movieDAO.getTopRatedMovies();
        assertEquals(2, topRatedMovies.size());
        assertEquals("Movie 1", topRatedMovies.get(0).getTitle());
    }

    @Test
    void testGetLowestRatedMovies() {
        Movie movie1 = new Movie();
        movie1.setTitle("Movie 1");
        movie1.setRating(5.0);
        movie1.setId(12345634L);

        Movie movie2 = new Movie();
        movie2.setTitle("Movie 2");
        movie2.setRating(6.0);
        movie2.setId(123452634L);

        movieDAO.create(movie1);
        movieDAO.create(movie2);

        List<Movie> lowestRatedMovies = movieDAO.getLowestRatedMovies();
        assertEquals(2, lowestRatedMovies.size());
        assertEquals("Movie 1", lowestRatedMovies.get(0).getTitle());
    }

    @Test
    void testGetMostPopularMovies() {
        Movie movie1 = new Movie();
        movie1.setTitle("Movie 1");
        movie1.setPopularity(100.00);
        movie1.setId(12345634L);

        Movie movie2 = new Movie();
        movie2.setTitle("Movie 2");
        movie2.setPopularity(50.00);
        movie2.setId(123445634L);

        movieDAO.create(movie1);
        movieDAO.create(movie2);

        List<Movie> mostPopularMovies = movieDAO.getMostPopularMovies();
        assertEquals(2, mostPopularMovies.size());
        assertEquals("Movie 1", mostPopularMovies.get(0).getTitle());
    }
}