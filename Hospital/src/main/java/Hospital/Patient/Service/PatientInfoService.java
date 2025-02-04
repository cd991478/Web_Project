package Hospital.Patient.Service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import Hospital.Patient.DTO.PatientInfoCreateDTO;
import Hospital.Patient.DTO.PatientInfoEditDTO;
import Hospital.Patient.DTO.PatientInfoEditResponseDTO;
import Hospital.Patient.DTO.PatientInfoReadDTO;
import Hospital.Patient.Entity.Patient;
import Hospital.Patient.Entity.PatientInfoRepository;


@Service
public class PatientInfoService {
	   
	   private PatientInfoRepository pir;
	   
	   public PatientInfoService(PatientInfoRepository pir) {
	      this.pir = pir;
	   }
	   
	   public Integer PatientInfoInsert(PatientInfoCreateDTO picDTO) {
		      Patient patient = Patient.builder()
		                        .P_Name(picDTO.getP_Name())
		                        .P_Gender(picDTO.getP_Gender())
		                        .P_Age(picDTO.getP_Age())
		                        .P_PhoneNumber(picDTO.getP_PhoneNumber())
		                        .P_Address1(picDTO.getP_Address1())
		                        .P_Address2(picDTO.getP_Address2())
		                        .P_TakingPill(picDTO.getP_TakingPill())
		                        .P_Nose(picDTO.getP_Nose())
		                        .P_Cough(picDTO.getP_Cough())
		                        .P_Pain(picDTO.getP_Pain())
		                        .P_Diarrhea(picDTO.getP_Diarrhea())
		                        .P_HighRiskGroup(picDTO.getP_HighRiskGroup())
		                        .P_VAS(picDTO.getP_VAS())
		                        .P_Agreement(picDTO.getP_Agreement())
		                        .build();
		      this.pir.save(patient);
		      return patient.getP_Id();
		   }
	   
	   public PatientInfoReadDTO PatientInfoRead(Integer P_Id) throws NoSuchElementException{
		      Patient patient = this.pir.findById(P_Id).orElseThrow();
		      return PatientInfoReadDTO.PatientInfoFactory(patient);
		   }
	   
	   public PatientInfoEditResponseDTO PatientInfoEdit(Integer P_Id) throws NoSuchElementException{
		   	Patient patient = this.pir.findById(P_Id).orElseThrow();
		   	return PatientInfoEditResponseDTO.PatientFactory(patient);
	   }
	   
	   public void PatientInfoUpdate(PatientInfoEditDTO pieDTO) throws NoSuchElementException{
		   Patient patient = this.pir.findById(pieDTO.getP_Id()).orElseThrow();
		   patient = pieDTO.Fill(patient);
		   this.pir.save(patient);
	   }

}