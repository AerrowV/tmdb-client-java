package dat.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Genre {

    @Id
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "genres", cascade = CascadeType.PERSIST)
    private Set<Movie> movies = new HashSet<>();

}
