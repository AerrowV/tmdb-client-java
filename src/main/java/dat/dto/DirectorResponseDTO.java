package dat.dto;

import lombok.Data;

import java.util.List;

@Data
public class DirectorResponseDTO {
    private List<CrewMemberDTO> crew; // List of crew members

    public DirectorDTO getDirector() {
        // Assuming you want the first director from the crew list
        return crew.stream()
                .filter(crewMember -> "Director".equals(crewMember.getJob()))
                .map(crewMember -> new DirectorDTO(crewMember.getId(), crewMember.getName()))
                .findFirst()
                .orElse(null);  // Return null if no director is found
    }
}
