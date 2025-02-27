package dat.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Genre {
    @Id
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "genres", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Set<Movie> movies = new HashSet<>();

}
