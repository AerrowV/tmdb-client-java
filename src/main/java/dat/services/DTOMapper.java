package dat.services;

import dat.dto.ActorDTO;
import dat.dto.DirectorDTO;
import dat.dto.GenreDTO;
import dat.dto.MovieDTO;
import dat.entities.Actor;
import dat.entities.Director;
import dat.entities.Genre;
import dat.entities.Movie;

import java.util.Collection;
import java.util.List;

public class DTOMapper {

    public static Movie movieToEntity(MovieDTO dto, Collection<Genre> genres, Director director, List<Actor> actors) {
        Movie movie = new Movie();
        movie.setId(dto.getId());
        movie.setTitle(dto.getTitle());
        movie.setReleaseDate(dto.getReleaseDate());
        movie.setRating(dto.getRating());
        movie.setPopularity(dto.getPopularity());
        movie.setOverview(dto.getOverview());
        movie.setGenres(genres);
        movie.setDirector(director);
        movie.setActor(actors);
        return movie;
    }
    public static Actor actorToEntity(ActorDTO actorDTO) {
        Actor actor = new Actor();
        actor.setId(actorDTO.getId());
        actor.setName(actorDTO.getName());  // Assuming ActorDTO has a 'name' field
        // Add more fields as necessary
        return actor;
    }
    public static Genre genreToEntity(GenreDTO genreDTO) {
        Genre genre = new Genre();
        genre.setId(genreDTO.getId());
        genre.setName(genreDTO.getName());
        return genre;
    }
    public static Director directorToEntity(DirectorDTO directorDTO) {
        Director director = new Director();
        director.setId(directorDTO.getId());
        director.setName(directorDTO.getName());  // Assuming DirectorDTO has a 'name' field
        // Add more fields as necessary
        return director;
    }
}
