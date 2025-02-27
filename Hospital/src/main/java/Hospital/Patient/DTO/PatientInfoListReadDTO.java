package Hospital.Patient.DTO;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientInfoListReadDTO {
	
		private Integer P_Id;
	   private String P_UserId;
	    
	   private String P_Name;
	 
	   private String P_Gender;
	   private Integer P_RegNum;
	   private String P_Phone;
	         
	   private String P_Address1;
	   private String P_Address2;
	         
	   private Integer P_TakingPill;
	         
	   private Integer P_Nose;
	   private Integer P_Cough;
	   private Integer P_Pain;
	   private Integer P_Diarrhea;
	         
	   private String P_HighRiskGroup;
	         
	   private Integer P_VAS;
	         
	   private Integer P_Agreement;
	         
	   private LocalDateTime P_InsertDateTime; 
}
