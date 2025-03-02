package dat.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Director {
    @Id
    private Long id;
    private String name;

    @OneToMany(mappedBy = "director", cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @ToString.Exclude
    private Set<Movie> movies = new HashSet<>();

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
