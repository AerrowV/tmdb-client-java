package dat;

import dat.daos.MovieDAO;
import dat.dto.MovieDTO;
import dat.entities.Actor;
import dat.entities.Director;
import dat.entities.Genre;
import dat.entities.Movie;
import dat.services.DTOMapper;

import java.util.Collection;
import java.util.List;

import static dat.services.FetchDanishMovies.*;

public class Main {
    public static void main(String[] args) {

        List<Long> movieIds = fetchMovieIds();  // Fetch list of movie IDs

        for (Long movieId : movieIds) {
            MovieDTO movieDTO = fetchMovieDetails(movieId);// Fetch movie details as DTO
            List<Actor> actorDTOs = fetchActorDetails(movieId);
            Collection<Genre> genreDTOs = fetchGenreDetails(movieId);
            Director director = fetchDirectorDetails(movieId);

            if (movieDTO != null) {
                DTOMapper mapper = new DTOMapper();
                // Convert MovieDTO to Movie entity
                Movie movie = mapper.movieToEntity(movieDTO, genreDTOs, director, actorDTOs);

                // Save Movie entity to the database
                MovieDAO movieDAO = new MovieDAO();
                movieDAO.save(movie);
            }
        }
    }
    public static Movie convertDTOToEntity(MovieDTO movieDTO) {
        Movie movie = new Movie();

        // Map each field from MovieDTO to Movie entity
        movie.setId((movieDTO.getId())); // Map the id
        movie.setTitle(movieDTO.getTitle()); // Map the title
        movie.setReleaseDate(movieDTO.getReleaseDate()); // Map the release date
        movie.setRating(movieDTO.getRating()); // Map the rating
        movie.setPopularity(movieDTO.getPopularity()); // Map the popularity
        movie.setOverview(movieDTO.getOverview()); // Map the overview

        return movie; // Return the populated Movie entity
    }

}