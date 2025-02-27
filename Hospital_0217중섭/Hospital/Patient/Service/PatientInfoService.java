package Hospital.Patient.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import Hospital.Patient.DTO.PatientInfoCreateDTO;
import Hospital.Patient.DTO.PatientInfoEditDTO;
import Hospital.Patient.DTO.PatientInfoEditResponseDTO;
import Hospital.Patient.DTO.PatientInfoReadDTO;
import Hospital.Patient.Entity.Patient;
import Hospital.Patient.Entity.PatientInfoRepository;
import jakarta.servlet.http.HttpSession;


@Service
public class PatientInfoService {
	   
	   @Autowired
	   private PatientInfoRepository pir;
	   private final PatientBinaryTree patientTree = new PatientBinaryTree();
		/*
		 * public PatientInfoService(PatientInfoRepository pir) { this.pir = pir; }
		 */
	   
	   public List<Patient> findAll(){
		   return this.pir.findAll();
	   }
	   
	   public Integer PatientInfoInsert(PatientInfoCreateDTO picDTO, HttpSession session) {//PatientInfoService
           Patient patient = Patient.builder()
                          .P_UserId(picDTO.getP_UserId())
                             .P_Name(picDTO.getP_Name())
                             .P_Gender(picDTO.getP_Gender())
                             .P_RegNum(picDTO.getP_RegNum())
                             .P_Phone(picDTO.getP_Phone())
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
           
        // 중복 검사 (이미 존재하는 경우 저장하지 않음)
	        if (patientTree.search(patient.getP_UserId()) == null) {
	            this.pir.save(patient); // DB에 저장
	            patientTree.insert(patient);// 이진트리에도 저장
	            System.out.println(patientTree);
	            return patient.getP_Id();
	        } else {
	            throw new IllegalArgumentException("이미 존재하는 환자입니다.");
	        }
          // P_Address = patient.getP_Address1();
//           String P_Address = patient.getP_Address1().split(" ")[2];
//           session.setAttribute("P_Address", P_Address);
        
           //System.out.println(session.getAttribute("P_Address"));
           //session.setAttribute("P_Address", picDTO.getP_Address1());
           //System.out.println(session.getAttributeNames());
        
        }
	   
	   public PatientInfoReadDTO PatientInfoRead(Integer P_Id, HttpSession session) throws NoSuchElementException{
           Patient patient = this.pir.findById(P_Id).orElseThrow();
           
           String P_Address1 = patient.getP_Address1().split(" ")[1];//달서구
           String P_Address2 = patient.getP_Address1().split(" ")[2];//구마로
            session.setAttribute("P_Address1", P_Address1);
            session.setAttribute("P_Address2", P_Address2);
           return PatientInfoReadDTO.PatientInfoFactory(patient);
        }
	   public List<Patient> PatientInfoList(String UserId) {
		   List<Patient> PatientInfoList = new ArrayList<Patient>();
		   List<Patient> patientinfo = this.findAll();
		   for(Patient patient: patientinfo) {
			   if((patient.getP_UserId()!=null) && (patient.getP_UserId().equals(UserId))) {
				   PatientInfoList.add(patient);
				   //System.out.println(PatientInfoList);
			   }
		   }
		   if(PatientInfoList.isEmpty()) {
			   PatientInfoList = null;
		   }
		   return PatientInfoList;
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
	   
	   public void PatientInfoDelete(Integer P_Id) {
		   Patient patient = this.pir.findById(P_Id).orElse(null);
		   if(patient != null) {
			   this.pir.deleteById(P_Id);
		   }
	   }
	   

	   
	   

}