package dat.dto;

import lombok.Data;

@Data
public class CrewMemberDTO {
        private Long id;
        private String name;
        private String job;  // "Director" or other jobs (like writer, producer, etc.)
        private String profilePath;
}
