package Hospital.Patient.Controller;

import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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
import Hospital.Patient.Service.PatientInfoService;

@Controller
public class PatientInfoController {
	 @Autowired
	   private PatientInfoService pis;
	   
	   @GetMapping("/PatientInfo/Input") // 문진표 입력 받는 화면 출력 실행 함수
	   public String PatientInfoInputPrint() {
	      return "PatientInfo/Input";
	   }
	   
	   
	   
	   @PostMapping("/PatientInfo/Input") // 문진표 입력 받는 화면에서 제출 버튼 누르면 이동하는 함수
	   public String PatientInfoInputInsert(PatientInfoCreateDTO picDTO) {
	      Integer PatientId = this.pis.PatientInfoInsert(picDTO);
	      return String.format("redirect:/PatientInfo/Result/%s", PatientId);
	   }
	   
	   
	   
	   @GetMapping("/PatientInfo/Result/{PatientId}") // 제출버튼시 결과창 화면 이동 함수
	   public ModelAndView PatientInfoInputResult(@PathVariable("PatientId") Integer PatientId) throws NoSuchElementException{
	      ModelAndView mav = new ModelAndView();
	      PatientInfoReadDTO pirDTO = this.pis.PatientInfoRead(PatientId);
	      mav.addObject("PatientData",pirDTO);
	      mav.setViewName("PatientInfo/Result");
	      return mav;
	   }
	   
	   @GetMapping("/PatientInfo/Edit/{PatientId}") // 결과창에서 수정버튼 누르면 이동하는 함수
	   public ModelAndView PatientInfoEdit(@PathVariable("PatientId") Integer PatientId) throws NoSuchElementException{
		  ModelAndView mav = new ModelAndView();
		  PatientInfoEditResponseDTO pierDTO = this.pis.PatientInfoEdit(PatientId);
		  mav.addObject("PatientInfoEdit",pierDTO);
		  mav.setViewName("PatientInfo/Edit");
		  return mav;
	   }
	   
	   @PostMapping("/PatientInfo/Edit/{PatientId}") // 수정 화면에서 완료 버튼 누를시 완료, 이동하는 함수
	   public ModelAndView PatientInfoUpdate(@Validated PatientInfoEditDTO pieDTO, Errors errors) {
	
		   this.pis.PatientInfoUpdate(pieDTO);
		   ModelAndView mav = new ModelAndView();
		   mav.setViewName(String.format("redirect:/PatientInfo/Result/%s",pieDTO.getPatientId()));
		   return mav;
	   }
	   
	   @ExceptionHandler(NoSuchElementException.class)
		public ModelAndView noSuchElementExceptionHandler(NoSuchElementException ex) {
			return this.error422("문진표 정보가 없습니다.","/PatientInfo");
		}
		
		private ModelAndView error422(String message, String location) {
			ModelAndView mav = new ModelAndView();
			mav.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
			mav.addObject("message",message);
			mav.addObject("location",location);
			mav.setViewName("common/error/422");
			return mav;
		}
	   
}