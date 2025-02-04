package Hospital.Patient.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import Hospital.Patient.DTO.PatientInfoCreateDTO;
import Hospital.Patient.DTO.PatientInfoEditDTO;
import Hospital.Patient.DTO.PatientInfoEditResponseDTO;
import Hospital.Patient.DTO.PatientInfoReadDTO;
import Hospital.Patient.Entity.D_Hospital;
import Hospital.Patient.Service.D_HospitalService;
import Hospital.Patient.Service.PatientInfoService;

@Controller
public class PatientInfoController {
	
	   @Autowired
	   private PatientInfoService pis;
	   @Autowired
	   private D_HospitalService d_hs;
	   // 환자 주소
	   String[] p_address = new String[5];//우편번호 api 사용 시 출력되는 값의 자료형에 따라 추후 변경
	   String address; // DB에서 가져온 주소
	   List<D_Hospital> D_HospitalList = new ArrayList<>();
	 
	   @GetMapping("/PatientInfo/Input") // 문진표 입력 받는 화면 출력 실행 함수
	   public String PatientInfoInput() {
	      return "PatientInfo/Input";
	   }
	   
	   
	   
	   @PostMapping("/PatientInfo/Input") // 문진표 입력 받는 화면에서 제출 버튼 누르면 이동하는 함수
	   public String PatientInfoInsert(PatientInfoCreateDTO picDTO) {
	      Integer P_Id = this.pis.PatientInfoInsert(picDTO);
	      return String.format("redirect:/PatientInfo/Result/%s", P_Id);
	   }
	   
	   
	   
	   @GetMapping("/PatientInfo/Result/{P_Id}") // 제출버튼시 결과창 화면 이동 함수
	   public ModelAndView PatientInfoResult(@PathVariable("P_Id") Integer P_Id) throws NoSuchElementException{
		  ModelAndView mav = new ModelAndView();
	      PatientInfoReadDTO pirDTO = this.pis.PatientInfoRead(P_Id);
	      p_address = pirDTO.getP_Address1().split(" ");
	      mav.addObject("PatientData",pirDTO);
	      mav.setViewName("PatientInfo/Result");
	      address = p_address[2];
	      //System.out.println(address);
	      return mav;
	   }
	   
	   @GetMapping("/PatientInfo/Edit/{P_Id}") // 결과창에서 수정버튼 누르면 이동하는 함수
	   public ModelAndView PatientInfoEdit(@PathVariable("P_Id") Integer P_Id) throws NoSuchElementException{
		  ModelAndView mav = new ModelAndView();
		  PatientInfoEditResponseDTO pierDTO = this.pis.PatientInfoEdit(P_Id);
		  mav.addObject("PatientInfoEdit",pierDTO);
		  mav.setViewName("PatientInfo/Edit");
		  return mav;
	   }
	   
	   @PostMapping("/PatientInfo/Edit/{P_Id}") // 수정 화면에서 완료 버튼 누를시 완료, 이동하는 함수
	   public ModelAndView PatientInfoUpdate(@Validated PatientInfoEditDTO pieDTO, Errors errors) {
	
		   this.pis.PatientInfoUpdate(pieDTO);
		   ModelAndView mav = new ModelAndView();
		   mav.setViewName(String.format("redirect:/PatientInfo/Result/%s",pieDTO.getP_Id()));
		   return mav;
	   }
	   
	   @ExceptionHandler(NoSuchElementException.class)
		public ModelAndView NoSuchElementExceptionHandler(NoSuchElementException ex) {
			return this.Error422("문진표 정보가 없습니다.","/PatientInfo");
		}
		
		private ModelAndView Error422(String message, String location) {
			ModelAndView mav = new ModelAndView();
			mav.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
			mav.addObject("Message",message);
			mav.addObject("Location",location);
			mav.setViewName("common/error/422"); // 추후 추가
			return mav;
		}
		
		@GetMapping("/PatientInfo/HospitalList")
		public ModelAndView GetAllHospitals() {
			//System.out.println(address);
			D_HospitalList.removeAll(D_HospitalList);
			List<D_Hospital> hospitals = d_hs.findAll();
			
			for(D_Hospital hospital: hospitals) {
				if(hospital.getH_Address().contains(address)){
					System.out.println(hospital.getH_Address());
					D_HospitalList.add(hospital);
				}
			}
			
			ModelAndView mav = new ModelAndView("HospitalList");
			mav.addObject("D_HospitalList", D_HospitalList);
			mav.setViewName("PatientInfo/HospitalList");
			return mav;
		}
	   
}