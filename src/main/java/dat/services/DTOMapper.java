package dat.services;

import dat.dto.ActorDTO;
import dat.dto.MovieDTO;
import dat.entities.Actor;
import dat.entities.Director;
import dat.entities.Genre;
import dat.entities.Movie;

import java.util.Collection;
import java.util.List;

public class DTOMapper {

    public static Movie toEntity(MovieDTO dto, Collection<Genre> genres, Director director, List<Actor> actors) {
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
    public static Actor toEntity(ActorDTO dto) {
        Actor actor = new Actor();
        actor.setId(dto.getId());
        actor.setName(dto.getName());
        return actor;
    }
}

