package Hospital.Appointment.DTO;

import java.time.LocalDateTime;

import Hospital.Appointment.Entity.Appointment;
import Hospital.Patient.Entity.D_Hospital;
import Hospital.User.Entity.UserInfo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentListResponseDTO {
	private Long id;
	private UserInfo user;
	private String hospitalname;
	private LocalDateTime createdTime;
	
	public AppointmentListResponseDTO(Long id, String hospitalname, LocalDateTime createdtime) {
		this.id = id;
		this.hospitalname = hospitalname;
		this.createdTime = createdtime;
	}
	
}
