package dat.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Movie {

    @Id
    @Column(unique = true)
    private Long id;
    private String title;
    private String releaseDate;
    private Double rating;
    private Double popularity;

    @Column(length = 10000)
    private String overview;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "movie_genre",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id")
    )
    private Set<Genre> genres = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "director_id")
    private Director director;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "movie_actor",
            joinColumns = @JoinColumn(name = "movie_id"),
            inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private Set<Actor> actor = new HashSet<>();

    @Override
    public String toString() {
        String directorName = (director != null) ? director.getName() : "Unknown";
        String actorNames = actor.stream().map(Actor::getName).toList().toString();
        String genreNames = genres.stream().map(Genre::getName).toList().toString();

        return "\n==============================" +
                "\n🎬 Movie: " + title +
                "\n📅 Release Date: " + releaseDate +
                "\n⭐ Rating: " + rating +
                "\n🔥 Popularity: " + popularity +
                "\nΦ Overview: " + overview +
                "\n🎭 Actors: " + actorNames +
                "\n🎞 Genres: " + genreNames +
                "\n🎬 Director: " + directorName +
                "\n==============================\n";
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
