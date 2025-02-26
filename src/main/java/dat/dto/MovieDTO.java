package dat.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDTO {

    private Long id;
    private String title;

    @JsonProperty("release_date")
    private String releaseDate;

    @JsonProperty("vote_average")
    private Double rating;

    @JsonProperty("popularity")
    private Double popularity;

    @JsonProperty("genre_ids")
    private List<Integer> genreIds;

    @JsonProperty("overview")
    private String overview;
}
