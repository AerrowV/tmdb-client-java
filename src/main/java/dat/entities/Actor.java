package dat.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Actor {
    @Id
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "actor", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @ToString.Exclude
    private Set<Movie> movies;

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}