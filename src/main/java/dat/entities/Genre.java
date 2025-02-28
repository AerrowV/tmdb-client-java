package dat.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Objects;
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

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
