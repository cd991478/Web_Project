package Hospital.Patient.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PatientInfoListResponseDTO {
	private Integer P_Id;
	private LocalDateTime P_InsertDateTime;
}
