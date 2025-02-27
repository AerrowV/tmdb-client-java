package dat.dto;

import dat.entities.Genre;
import lombok.Data;

import java.util.List;

@Data
public class GenreResponseDTO {
    private List<GenreDTO> genres;
}
