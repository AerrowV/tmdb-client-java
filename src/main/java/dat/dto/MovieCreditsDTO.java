package dat.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieCreditsDTO {

    @JsonProperty("cast")
    private Set<ActorDTO> cast;

    @JsonProperty("crew")
    private Set<DirectorDTO> crew;
}
