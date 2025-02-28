package dat.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieResponseDTO {
    public int page;
    public List<MovieDTO> results;
    public int total_pages;
    public int total_results;
}
