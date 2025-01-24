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
		      Patient patient_data = Patient.builder()
		                        .Name(picDTO.getName())
		                        .Gender(picDTO.getGender())
		                        .Age(picDTO.getAge())
		                        .Phone_Number(picDTO.getPhone_Number())
		                        .Address_1(picDTO.getAddress_1())
		                        .Address_2(picDTO.getAddress_2())
		                        .Taking_Pill(picDTO.getTaking_Pill())
		                        .Nose(picDTO.getNose())
		                        .Cough(picDTO.getCough())
		                        .Pain(picDTO.getPain())
		                        .Diarrhea(picDTO.getDiarrhea())
		                        .High_Risk_Group(picDTO.getHigh_Risk_Group())
		                        .VAS(picDTO.getVAS())
		                        .Agreement(picDTO.getAgreement())
		                        .build();
		      this.pir.save(patient_data);
		      return patient_data.getPatientId();
		   }
	   
	   public PatientInfoReadDTO PatientInfoRead(Integer PatientId) throws NoSuchElementException{
		      Patient p = this.pir.findById(PatientId).orElseThrow();
		      return PatientInfoReadDTO.PatientInfoFactory(p);
		   }
	   
	   public PatientInfoEditResponseDTO PatientInfoEdit(Integer PatientId) throws NoSuchElementException{
		   	Patient p = this.pir.findById(PatientId).orElseThrow();
		   	return PatientInfoEditResponseDTO.PatientFactory(p);
	   }
	   
	   public void PatientInfoUpdate(PatientInfoEditDTO pieDTO) throws NoSuchElementException{
		   Patient p = this.pir.findById(pieDTO.getPatientId()).orElseThrow();
		   p = pieDTO.fill(p);
		   this.pir.save(p);
	   }

}