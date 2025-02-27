package dat.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Actor {
    @Id
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "actor", cascade = CascadeType.PERSIST)
    private Set<Movie> movies;
}