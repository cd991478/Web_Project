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
	private Integer PatientId;
	
	@NonNull
	@NotBlank
	private String Name;
	@NonNull
	private String Gender;
	@NotNull
	
	private Integer Age;
	@NonNull
	@NotBlank
	private String Phone_Number;
	@NonNull
	@NotBlank
	private String Address_1;
	@NonNull
	@NotBlank
	private String Address_2;
	@NonNull
	private Integer Taking_Pill;
	@NonNull
	private Integer Nose;
	@NonNull
	private Integer Cough;
	@NonNull
	private Integer Pain;
	@NonNull
	private Integer Diarrhea;
	@NonNull
	private String High_Risk_Group;
	@NonNull
	private Integer VAS;
	// AgreeMent
	
	public Patient fill(Patient p) {
		p.setName(this.Name);
		p.setGender(this.Gender);
		p.setAge(this.Age);
		p.setPhone_Number(this.Phone_Number);
		p.setAddress_1(this.Address_1);
		p.setAddress_2(this.Address_2);
		p.setTaking_Pill(this.Taking_Pill);
		p.setNose(this.Nose);
		p.setCough(this.Cough);
		p.setPain(this.Pain);
		p.setDiarrhea(this.Diarrhea);
		p.setHigh_Risk_Group(this.High_Risk_Group);
		p.setVAS(this.VAS);
		return p;
	}
	
	
}
