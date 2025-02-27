package Hospital.Patient.Service;

import java.util.ArrayList;
import java.util.List;
//import java.util.stream.Collectors;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Hospital.Patient.DTO.D_HospitalListDTO;
import Hospital.Patient.DTO.D_HospitalReadDTO;
import Hospital.Patient.DTO.PatientInfoListResponseDTO;
import Hospital.Patient.Entity.D_Hospital;
import Hospital.Patient.Entity.D_HospitalRepository;
import Hospital.Patient.Entity.Patient;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class D_HospitalService {
	@Autowired
	private D_HospitalRepository d_hr;
	//@Autowired
	//private PatientInfoRepository patientInfoRepository;
	
	public List<D_Hospital> findAll(){
		return d_hr.findAll();
	}
	
	
	
	public D_HospitalReadDTO d_hrRead(Integer id) throws NoSuchElementException{
		D_Hospital hospital = this.d_hr.findById(id).orElseThrow();
		return D_HospitalReadDTO.D_HospitalFactory(hospital);
	}
	
	public List<D_Hospital> findAll(HttpServletRequest request){
        
        HttpSession session = request.getSession();
        String sessionAddress1 = (String) session.getAttribute("P_Address1");//달서구
        String sessionAddress2 = (String) session.getAttribute("P_Address2");//구마로
        System.out.println(sessionAddress1);
        List<D_Hospital> allHospitals = d_hr.findAll();
        List<D_Hospital> filteredHospitals = new ArrayList<>();
        for (D_Hospital hospital : allHospitals) {
            if (hospital.getH_Address().contains(sessionAddress1)) {
                if(hospital.getH_Address().contains(sessionAddress2)) {
                   filteredHospitals.add(hospital);
            
                }   
            }
        }
        return filteredHospitals;
   }
	
	public List<D_Hospital> FindAllHospital(){
		List<D_Hospital> allHospitals = d_hr.findAll();
		List<D_Hospital> filteredHospitals = new ArrayList<>();
		for(D_Hospital hospital : allHospitals) {
			filteredHospitals.add(hospital);
			
		}
		return filteredHospitals;
	}
	
	public List<D_HospitalListDTO> D_HospitalListSearchPage(Integer page, Integer maxsize, String H_Name, String H_Region) {
	    // 페이지 번호가 null이면 첫 페이지로 기본값 설정
	    if (page == null || page < 0) {
	        page = 0;
	    }
	    // 전체 데이터 조회
	    List<D_Hospital> allhospital = this.d_hr.findAll();  // 전체 데이터 조회
	    List<D_Hospital> filteredhospital = allhospital.stream()
	                                                .filter(hospital -> hospital.getH_Region().contains(H_Region) && hospital.getH_Name().contains(H_Name))
	                                                .collect(Collectors.toList());
	    // 전체 데이터 개수
	    long totalElements = filteredhospital.size();
	    // 전체 페이지 수 계산
	    int totalPages = (int) Math.ceil((double) totalElements / maxsize);
	    // 페이지네이션: 현재 페이지의 시작 인덱스 계산
	    int startIndex = page * maxsize;
	    int endIndex = Math.min(startIndex + maxsize, filteredhospital.size());
	    // 현재 페이지에 해당하는 데이터만 잘라서 가져오기
	    List<D_Hospital> pagedhospital = filteredhospital.subList(startIndex, endIndex);
	    // DTO로 변환하여 반환
	    return pagedhospital.stream()
	                         .map(hospital -> new D_HospitalListDTO(hospital.getH_Id(),hospital.getH_Region(),
	                        		 hospital.getH_Name(),
	                        		 hospital.getH_Department(),hospital.getBed_Total(),hospital.getBed_General(),
	                        		 hospital.getBed_Psy(),hospital.getBed_Nursing(),hospital.getBed_Intensive(),
	                        		 hospital.getBed_Isolation(),hospital.getBed_Clean(),hospital.getH_Phone_Number(),
	                        		 hospital.getH_Address()))
	                         .collect(Collectors.toList());
	}
	
	
//	public List<D_HospitalReadDTO> findAll(){
//		return d_hr.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
//	}
	
	public D_HospitalReadDTO Save(D_HospitalReadDTO d_hrDTO) {
		D_Hospital h = ConvertToEntity(d_hrDTO);
		D_Hospital saveh = d_hr.save(h);
		return ConvertToDTO(saveh);
	}
	
	private D_HospitalReadDTO ConvertToDTO(D_Hospital h) {
		D_HospitalReadDTO d_hrDTO = new D_HospitalReadDTO();
		d_hrDTO.setH_Id(h.getH_Id());
		d_hrDTO.setH_Name(h.getH_Name());
		d_hrDTO.setH_Department(h.getH_Department());
		d_hrDTO.setBed_Total(h.getBed_Total());
		d_hrDTO.setBed_General(h.getBed_General());
		d_hrDTO.setBed_Psy(h.getBed_Psy());
		d_hrDTO.setBed_Nursing(h.getBed_Nursing());
		d_hrDTO.setBed_Clean(h.getBed_Clean());
		d_hrDTO.setBed_Intensive(h.getBed_Intensive());
		d_hrDTO.setBed_Isolation(h.getBed_Isolation());
		d_hrDTO.setBed_Clean(h.getBed_Clean());
		d_hrDTO.setH_Phone_Number(h.getH_Phone_Number());
		d_hrDTO.setH_Address(h.getH_Address());
		return d_hrDTO;
	}
	
	private D_Hospital ConvertToEntity(D_HospitalReadDTO d_hrDTO) {
		D_Hospital h = new D_Hospital();
		h.setH_Id(h.getH_Id());
		h.setH_Name(h.getH_Name());
		h.setH_Department(h.getH_Department());
		h.setBed_Total(h.getBed_Total());
		h.setBed_General(h.getBed_General());
		h.setBed_Psy(h.getBed_Psy());
		h.setBed_Nursing(h.getBed_Nursing());
		h.setBed_Clean(h.getBed_Clean());
		h.setBed_Intensive(h.getBed_Intensive());
		h.setBed_Isolation(h.getBed_Isolation());
		h.setBed_Clean(h.getBed_Clean());
		h.setH_Phone_Number(h.getH_Phone_Number());
		h.setH_Address(h.getH_Address());
		return h;
	}
	
}