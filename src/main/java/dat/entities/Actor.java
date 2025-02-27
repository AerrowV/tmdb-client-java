package dat.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Actor {
    @Id
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "actor", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Movie> movies;
}