package Hospital.Patient.DTO;

import Hospital.Patient.Entity.Patient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class PatientInfoEditDTO {
	
	@NonNull
	private Integer P_Id;
	
	@NonNull
	@NotBlank
	private String P_Name;
	@NonNull
	private String P_Gender;
	@NotNull
	
	private Integer P_Age;
	@NonNull
	@NotBlank
	private String P_PhoneNumber;
	@NonNull
	@NotBlank
	private String P_Address1;
	@NonNull
	@NotBlank
	private String P_Address2;
	@NonNull
	private Integer P_TakingPill;
	@NonNull
	private Integer P_Nose;
	@NonNull
	private Integer P_Cough;
	@NonNull
	private Integer P_Pain;
	@NonNull
	private Integer P_Diarrhea;
	@NonNull
	private String P_HighRiskGroup;
	@NonNull
	private Integer P_VAS;
	// AgreeMent
	
	public Patient Fill(Patient p) {
		p.setP_Name(this.P_Name);
		p.setP_Gender(this.P_Gender);
		p.setP_Age(this.P_Age);
		p.setP_PhoneNumber(this.P_PhoneNumber);
		p.setP_Address1(this.P_Address1);
		p.setP_Address2(this.P_Address2);
		p.setP_TakingPill(this.P_TakingPill);
		p.setP_Nose(this.P_Nose);
		p.setP_Cough(this.P_Cough);
		p.setP_Pain(this.P_Pain);
		p.setP_Diarrhea(this.P_Diarrhea);
		p.setP_HighRiskGroup(this.P_HighRiskGroup);
		p.setP_VAS(this.P_VAS);
		return p;
	}
	
	
}
