package dat.dto;

import dat.entities.Movie;
import lombok.Data;

import java.util.List;

@Data
public class MovieResponseDTO {

    public List<MovieDTO> results;
}
