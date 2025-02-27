package dat.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ActorResponseDTO {

    private List<ActorDTO> actors = new ArrayList<>();  // Ensure it's initialized

    public List<ActorDTO> getActors() {
        return actors == null ? new ArrayList<>() : actors;  // Always return a non-null list
    }
}
