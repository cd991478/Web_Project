package Hospital.Patient.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import Hospital.Patient.DTO.PatientInfoCreateDTO;
import Hospital.Patient.DTO.PatientInfoEditDTO;
import Hospital.Patient.DTO.PatientInfoEditResponseDTO;
import Hospital.Patient.DTO.PatientInfoReadDTO;
import Hospital.Patient.Entity.D_Hospital;
import Hospital.Patient.Entity.Patient;
import Hospital.Patient.Service.D_HospitalService;
import Hospital.Patient.Service.PatientInfoService;
import Hospital.User.DTO.UserInfoDTO;
import Hospital.User.Service.UserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PatientInfoController {

	@Autowired
	private PatientInfoService pis;
	@Autowired
	private UserInfoService uis;
	@Autowired
	private D_HospitalService d_hs;
	// 환자 주소
	String[] p_address = new String[5];// 우편번호 api 사용 시 출력되는 값의 자료형에 따라 추후 변경
	String address1; // DB에서 가져온 주소
	String address2;
	List<D_Hospital> D_HospitalList = new ArrayList<>();

	@GetMapping("/PatientInfo/Input") // 문진표 입력 받는 화면 출력 실행 함수
	public ModelAndView PatientInfoInput(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		UserInfoDTO uiDTO = new UserInfoDTO();
		String UserId = (String) session.getAttribute("UserId");
		uiDTO = this.uis.UserInfoRead(UserId);
		mav.addObject("UserInfo", uiDTO);
		mav.setViewName("/PatientInfo/Input");
		return mav;
	}

	@PostMapping("/PatientInfo/Input") // 문진표 입력 받는 화면에서 제출 버튼 누르면 이동하는 함수
	public String PatientInfoInsert(PatientInfoCreateDTO picDTO, HttpSession session) {
		String P_UserId = (String) session.getAttribute("UserId");
		picDTO.setP_UserId(P_UserId);
		Integer P_Id = this.pis.PatientInfoInsert(picDTO, session);
		session.setAttribute("P_Id", P_Id);
//		  return String.format("redirect:/PatientInfo/Result/%s", P_Id);
		return "redirect:/PatientInfo/Result/" + P_Id;
	}

	@GetMapping("/PatientInfo/Result/{P_Id}") // 제출버튼시 결과창 화면 이동 함수
	public ModelAndView PatientInfoResult(@PathVariable("P_Id") Integer P_Id, HttpSession session)
			throws NoSuchElementException {
		ModelAndView mav = new ModelAndView();
		// Integer P_Id = (Integer) session.getAttribute("P_Id");
		if (P_Id == null) {
			throw new NoSuchElementException("문진표 정보가 없습니다.");
		}
		PatientInfoReadDTO pirDTO = this.pis.PatientInfoRead(P_Id, session);
		String SessionUserId = (String) session.getAttribute("UserId");
		// 로그인된 사용자와 문진표 작성자가 일치하는지 확인
		if (!pirDTO.getP_UserId().equals(SessionUserId)) {
			throw new SecurityException("잘못된 접근입니다.");
		}

		p_address = pirDTO.getP_Address1().split(" ");

		mav.addObject("PatientData", pirDTO);
		address1 = p_address[1];// 달서구
		address2 = p_address[2];// 구마로
		mav.setViewName("PatientInfo/Result");

		session.setAttribute("P_Id", P_Id);
		return mav;
	}

	@GetMapping("/PatientInfo/Edit") // 결과창에서 수정버튼 누르면 이동하는 함수
	public ModelAndView PatientInfoEdit(HttpSession session) throws NoSuchElementException {
		ModelAndView mav = new ModelAndView();
		Integer P_Id = (Integer) session.getAttribute("P_Id");
		if (P_Id == null) {
			throw new NoSuchElementException("잘못된 접근입니다.");
		}
		PatientInfoEditResponseDTO pierDTO = this.pis.PatientInfoEdit(P_Id);
		mav.addObject("PatientInfoEdit", pierDTO);
		mav.setViewName("PatientInfo/Edit");
		return mav;
	}

	@PostMapping("/PatientInfo/Edit") // 수정 화면에서 완료 버튼 누를시 완료, 이동하는 함수
	public String PatientInfoUpdate(@Validated PatientInfoEditDTO pieDTO, HttpSession session, Errors errors) {
		ModelAndView mav = new ModelAndView();
		Integer P_Id = (Integer) session.getAttribute("P_Id");
		if (P_Id == null) {
			throw new NoSuchElementException("문진표 정보를 찾을 수 없습니다.");
		}
		session.setAttribute("P_Id", P_Id);
		pieDTO.setP_Id(P_Id);
		this.pis.PatientInfoUpdate(pieDTO);
		return "redirect:/PatientInfo/Result/" + P_Id;
	}

	@ExceptionHandler(NoSuchElementException.class)
	public ModelAndView NoSuchElementExceptionHandler(NoSuchElementException ex) {
		return this.Error422("문진표 정보가 없습니다.", "/PatientInfo");
	}

	private ModelAndView Error422(String message, String location) {
		ModelAndView mav = new ModelAndView();
		mav.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
		mav.addObject("Message", message);
		mav.addObject("Location", location);
		mav.setViewName("common/error/422"); // 추후 추가
		return mav;
	}

	@GetMapping("/PatientInfo/HospitalList") // 0207병원 목록 선별 메서드
	public ModelAndView getAllHospitals(HttpServletRequest request) {
		List<D_Hospital> hospitals = d_hs.findAll(request);
		ModelAndView mav = new ModelAndView("HospitalList");
		mav.addObject("D_HospitalList", hospitals);
		mav.setViewName("PatientInfo/HospitalList");
		return mav;
	}
	
	

	@GetMapping("/PatientInfo/List") // 문진표 목록 이동
	public ModelAndView PatientInfoList(HttpSession session, Model model) {
		String UserId = (String) session.getAttribute("UserId");
		UserInfoDTO uiDTO = this.uis.UserInfoRead(UserId);
		model.addAttribute("UserId", UserId);
		model.addAttribute("UserName", uiDTO.getUserName());

		
		List<Patient> PatientInfoList = new ArrayList<Patient>(); PatientInfoList =
		this.pis.PatientInfoList(UserId);
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("PatientInfoList", PatientInfoList);
		mav.setViewName("PatientInfo/List");
		return mav;
	}

	@GetMapping("/PatientInfo/Delete") // 문진표 삭제
	public String PatientInfoDelete(@RequestParam("P_Id") Integer P_Id, Model model) {
		this.pis.PatientInfoDelete(P_Id);
		model.addAttribute("message", "문진표가 삭제되었습니다.");
		return "redirect:/PatientInfo/List";
	}

}