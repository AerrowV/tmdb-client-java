package dat.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class DirectorDTO {

    private Long id;
    private String name;

    public DirectorDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

}