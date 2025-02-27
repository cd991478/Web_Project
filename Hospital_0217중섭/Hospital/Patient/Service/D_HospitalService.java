package Hospital.Patient.Service;

import java.util.ArrayList;
import java.util.List;
//import java.util.stream.Collectors;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Hospital.Appointment.DTO.AppointmentReadDTO;
import Hospital.Appointment.Entity.Appointment;
import Hospital.Patient.DTO.D_HospitalReadDTO;
import Hospital.Patient.Entity.D_Hospital;
import Hospital.Patient.Entity.D_HospitalRepository;
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