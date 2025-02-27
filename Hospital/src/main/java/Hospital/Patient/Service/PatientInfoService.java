package Hospital.Patient.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Hospital.Patient.DTO.PatientInfoCreateDTO;
import Hospital.Patient.DTO.PatientInfoEditDTO;
import Hospital.Patient.DTO.PatientInfoEditResponseDTO;
import Hospital.Patient.DTO.PatientInfoListResponseDTO;
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
           return patient.getP_Id();
           
        // 중복 검사 (이미 존재하는 경우 저장하지 않음)
//	        if (patientTree.search(patient.getP_UserId()) == null) {
//	            this.pir.save(patient); // DB에 저장
//	            patientTree.insert(patient);// 이진트리에도 저장
//	            System.out.println(patientTree);
//	            return patient.getP_Id();
//	        } else {
//	            throw new IllegalArgumentException("이미 존재하는 환자입니다.");
//	            
//	        }
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
	   
	   public List<PatientInfoListResponseDTO> PatientListPage(Integer page, Integer maxsize, String UserId) {
		    // 페이지 번호가 null이면 첫 페이지로 기본값 설정
		    if (page == null || page < 0) {
		        page = 0;
		    }

		    // 전체 Patient 데이터 조회 (모든 사용자에 대한 Patient 정보)
		    List<Patient> allPatients = this.pir.findAll();  // 전체 데이터 조회

		    // UserId에 맞는 Patient만 필터링
		    List<Patient> filteredPatients = allPatients.stream()
		                                                .filter(patient -> patient.getP_UserId().equals(UserId))  // UserId로 필터링
		                                                .collect(Collectors.toList());

		 // 필터링된 데이터를 날짜를 기준으로 내림차순 정렬
		    filteredPatients.sort((p1, p2) -> p2.getP_InsertDateTime().compareTo(p1.getP_InsertDateTime()));  // 내림차순 정렬
		    // 전체 데이터 개수
		    long totalElements = filteredPatients.size();

		    // 전체 페이지 수 계산
		    int totalPages = (int) Math.ceil((double) totalElements / maxsize);

		    // 페이지네이션: 현재 페이지의 시작 인덱스 계산
		    int startIndex = page * maxsize;
		    int endIndex = Math.min(startIndex + maxsize, filteredPatients.size());

		    // 현재 페이지에 해당하는 데이터만 잘라서 가져오기
		    List<Patient> pagedPatients = filteredPatients.subList(startIndex, endIndex);

		    // DTO로 변환하여 반환
		    return pagedPatients.stream()
		                         .map(patient -> new PatientInfoListResponseDTO(patient.getP_Id(), patient.getP_InsertDateTime()))
		                         .collect(Collectors.toList());
		}

	  
	   
//	public List<PatientInfoListResponseDTO> PatientListPage(Integer page, Integer maxsize, String UserId){
//		   if(page == null) {
//				page = 0;
//			}else {
//				page -= 1;
//			}
//		   Pageable pageable = PageRequest.of(page, maxsize);
//		   Page<Patient> patientpage = this.pir.findAll(pageable);
//		   
////		   for(int i = 0 ; i < patientpage.getContent().size(); i++) {
////			   if(patientpage.getContent().get(1).equals(UserId)) {
////				   System.out.println(patientpage.getContent().get(1));
//////				   patientpage.getContent().removeAll();
////				   
////			   }
////		   }
////			   for(int j = 0; j < patientpage.getContent().size();j++) {
////				   System.out.println(patientpage.getContent().get(j));
////			   }
//		   return patientpage.getContent().stream()
//		            .filter(patient -> patient.getP_UserId().equals(UserId))  // UserId를 비교할 때 equals() 사용
//		            .map(patient -> new PatientInfoListResponseDTO(patient.getP_Id(), patient.getP_InsertDateTime()))  // 원하는 객체로 변환
//		            .collect(Collectors.toList());
//
//	   }
	   
	   
	   

}