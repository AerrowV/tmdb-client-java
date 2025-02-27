package dat.dto;

import lombok.Data;

import java.util.List;

@Data
public class MovieResponseDTO {
    public int page;
    public List<MovieDTO> results;
    public int total_pages;
    public int total_results;
}
