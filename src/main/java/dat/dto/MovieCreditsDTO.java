package dat.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieCreditsDTO {

    @JsonProperty("cast")
    private List<ActorDTO> cast;

    @JsonProperty("crew")
    private List<DirectorDTO> crew;
}
