package dat.services;

import dat.dto.ActorDTO;
import dat.dto.DirectorDTO;
import dat.dto.GenreDTO;
import dat.dto.MovieDTO;
import dat.entities.Actor;
import dat.entities.Director;
import dat.entities.Genre;
import dat.entities.Movie;

import java.util.Set;

public class DTOMapper {

    public static Movie movieToEntity(MovieDTO dto, Set<Genre> genres, Director director, Set<Actor> actors) {
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

    public static Actor actorToEntity(ActorDTO dto) {
        Actor actor = new Actor();
        actor.setId(dto.getId());
        actor.setName(dto.getName());
        return actor;
    }

    public static Genre genreToEntity(GenreDTO dto) {
        Genre genre = new Genre();
        genre.setId(dto.getId());
        genre.setName(dto.getName());
        return genre;
    }

    public static Director directorToEntity(DirectorDTO dto) {
        Director director = new Director();
        director.setId(dto.getId());
        director.setName(dto.getName());
        return director;
    }
}

