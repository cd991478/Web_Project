package Hospital.Appointment.Controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Hospital.Appointment.DTO.AppointmentDTO;
import Hospital.Appointment.DTO.AppointmentReadDTO;
import Hospital.Appointment.Entity.Appointment;
import Hospital.Appointment.Service.AppointmentAsyncService;
import Hospital.Appointment.Service.AppointmentService;
import Hospital.Patient.DTO.D_HospitalListDTO;
import Hospital.Patient.DTO.D_HospitalReadDTO;
import Hospital.Patient.Entity.D_Hospital;
import Hospital.Patient.Entity.D_HospitalRepository;
import Hospital.Patient.Service.D_HospitalService;
import Hospital.User.DTO.UserInfoDTO;
import Hospital.User.Service.UserInfoService;
import jakarta.servlet.http.HttpSession;


@Controller
public class AppointmentController {


	@Autowired
    private D_HospitalRepository d_hr;
    
	@Autowired
    private AppointmentService appointmentService; // AppointmentService 추가
	
	@Autowired
	private UserInfoService uls;
	
	@Autowired
	private D_HospitalService d_hs;
    
    
    
    // 병원 목록을 검색하는 GET 요청 처리
    @GetMapping("/AppointmentPage/Input")
    public ModelAndView searchHospitals(
            @RequestParam(value = "H_Name", required = false) String H_Name,
            @RequestParam(value = "H_Region", required = false) String H_Region, HttpSession session) {

        System.out.println("======== 컨트롤러 진입 ========");
        System.out.println("H_Name: " + H_Name);
        System.out.println("H_Region: " + H_Region);
        String id = (String) session.getAttribute("UserId");
        System.out.println(id);

        // null 체크
        if (H_Name == null) H_Name = "";
        if (H_Region == null) H_Region = "";

        List<D_Hospital> allHospitalList = this.d_hr.findAll();
        System.out.println("DB에서 불러온 전체 병원 개수: " + allHospitalList.size());

        List<D_Hospital> filteredList = new ArrayList<>();
        for (D_Hospital hospital : allHospitalList) {
            if (hospital.getH_Region().contains(H_Region) && hospital.getH_Name().contains(H_Name)) {
                filteredList.add(hospital);
            }
        }

        System.out.println("검색된 병원 개수: " + filteredList.size());

        ModelAndView mav = new ModelAndView();
        mav.addObject("D_HospitalList", filteredList);
        mav.setViewName("AppointmentPage/Input");
        return mav;
    }
    
    @PostMapping("/AppointmentPage/Reserve")
    public String reserveAppointment(@RequestParam("selectedHospitals") Integer hospitalId,  // 선택된 병원 ID
                                     @RequestParam(value = "patientName") String patientName,                   // 환자 성명
                                     @RequestParam(value = "appointmentTime") LocalDateTime appointmentTime,
                                     HttpSession session// 예약 시간
                                   ) {                        // 사용자 ID
    	String userId = (String) session.getAttribute("UserId");
        System.out.println("hospitalId: " + hospitalId);
        System.out.println("patientName: " + patientName);
        System.out.println("appointmentTime: " + appointmentTime);
        System.out.println("userId: " + userId);
        // 예약 DTO로 데이터 전달
        AppointmentDTO appointmentDTO = new AppointmentDTO();
        appointmentDTO.setHospitalId(hospitalId);  // 병원 ID
        appointmentDTO.setPatientName(patientName); // 환자 성명
        appointmentDTO.setAppointmentTime(appointmentTime); // 예약 시간
        appointmentDTO.setUserId(userId); // 사용자 ID
  
        // 예약 처리 서비스 호출
       // appointmentService.createAppointment(appointmentDTO);
        
        AppointmentDTO savedAppointment = appointmentService.createAppointment(appointmentDTO);
        Long id = savedAppointment.getId(); // 

        System.out.println("아이디: " + id);
   
        return String.format("redirect:/AppointmentPage/Result/%s", id);
    }
    
    @GetMapping("/AppointmentPage/Result/{id}") // 제출버튼시 결과창 화면 이동 함수
	public ModelAndView AppointmentResult(@PathVariable("id") Long Id) throws NoSuchElementException{
		  ModelAndView mav = new ModelAndView();
		  UserInfoDTO uiDTO = new UserInfoDTO();
	      AppointmentReadDTO aprDTO = this.appointmentService.AppointmentRead(Id);
	      uiDTO = this.uls.UserInfoRead(aprDTO.getUserId());
	      System.out.println("아이디 "+aprDTO.getUserId());
	      System.out.println("병원아이디 "+aprDTO.getHospitalId());
	      System.out.println("예약아이디 "+aprDTO.getId());
	      System.out.println("예약시간"+aprDTO.getAppointmentTime());
	      System.out.println("시간"+aprDTO.getCreatedTime());
	      Integer id = aprDTO.getHospitalId();
	      D_HospitalReadDTO d_hrDTO = this.d_hs.d_hrRead(id);
	      mav.addObject("hospital", d_hrDTO);
	      mav.addObject("UserInfo",uiDTO);
	      mav.addObject("AppointmentInfo",aprDTO);
	      mav.setViewName("/AppointmentPage/Result");
	      return mav;
	   }
    
    @GetMapping("/AppointmentPage/List")
	public ModelAndView AppointmentList(HttpSession session, Model model) {
    	List<Appointment> appointmentList = new ArrayList<Appointment>();
    	
    	
		String UserId = (String)session.getAttribute("UserId");
		UserInfoDTO uiDTO = this.uls.UserInfoRead(UserId);
		model.addAttribute("UserId",UserId);
		model.addAttribute("UserName",uiDTO.getUserName());
		ModelAndView mav = new ModelAndView();
		List<Appointment> appointmentinfo = this.appointmentService.findAll();
		   for(Appointment appointment: appointmentinfo) {
			   if((appointment.getUser().getUserId().equals(UserId))) {
				    appointmentList.add(appointment);
				   //System.out.println("");
			   }
		   }
		   
		   if(appointmentinfo.isEmpty()) {
			   appointmentinfo = null;
			   mav.setViewName("AppointmentPage/List");
			   return mav;
		   }
		   if(appointmentList.isEmpty()) {
			   appointmentList = null;
			   mav.setViewName("AppointmentPage/List");
			   return mav;
		   }
		   mav.addObject("AppointmentList", appointmentList);
		   mav.setViewName("AppointmentPage/List");
		   return mav;
	   }
	

    
    @GetMapping("/AppointmentPage/Delete")
	public String PatientInfoDelete(@RequestParam("Id") Long Id, HttpSession session, Model model) {
		  this.appointmentService.AppointmentDelete(Id);
		  return "redirect:/AppointmentPage/List";
		 
	}
	
}