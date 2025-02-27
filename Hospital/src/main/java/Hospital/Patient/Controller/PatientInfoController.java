package Hospital.Patient.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
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
import Hospital.Patient.DTO.PatientInfoListResponseDTO;
import Hospital.Patient.DTO.PatientInfoReadDTO;
import Hospital.Patient.Entity.D_Hospital;
import Hospital.Patient.Entity.Patient;
import Hospital.Patient.Entity.PatientInfoRepository;
import Hospital.Patient.Service.D_HospitalService;
import Hospital.Patient.Service.PatientInfoService;
import Hospital.User.DTO.UserInfoDTO;
import Hospital.User.Service.UserInfoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class PatientInfoController {

	@Autowired
	private PatientInfoRepository pir;
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
	public ModelAndView PatientInfoList(@RequestParam(name="page", required=false) Integer page,
	                                    HttpSession session, Model model) {
	    final int PageSize = 5;
	    String UserId = (String) session.getAttribute("UserId");
	    UserInfoDTO uiDTO = this.uis.UserInfoRead(UserId);
	    model.addAttribute("UserId", UserId);
	    model.addAttribute("UserName", uiDTO.getUserName());

	    // 페이지 번호가 null일 경우 0으로 설정
	    if (page == null || page < 0) {
	        page = 0;  // 첫 페이지를 기본값으로 설정
	    }

	    // UserId에 해당하는 문진표 목록 가져오기
	    List<PatientInfoListResponseDTO> PatientInfoList = this.pis.PatientListPage(page, PageSize, UserId);

	    // UserId로 필터링된 전체 문진표 개수 구하기 (findAll()으로 전체 데이터 확인 후 필터링)
	    List<Patient> allPatients = this.pir.findAll();  // 전체 데이터 조회
	    long totalElements = allPatients.stream()
	                                    .filter(patient -> patient.getP_UserId().equals(UserId))  // UserId로 필터링
	                                    .count();  // 필터링된 Patient 목록의 개수

	    // 전체 페이지 수 계산
	    int totalPages = (int) Math.ceil((double) totalElements / PageSize);  // 전체 페이지 수 계산
	    int currentPage = page;  // 현재 페이지 번호
	    if(totalElements == 0) {
	    	PatientInfoList = null;
	    }
	    // 모델에 페이지네이션 정보 추가
	    model.addAttribute("PatientInfoList", PatientInfoList);
	    model.addAttribute("totalPages", totalPages);  // 전체 페이지 수
	    model.addAttribute("totalElements", totalElements);  // 전체 문진표 수
	    model.addAttribute("currentPage", currentPage);  // 현재 페이지 번호
	    model.addAttribute("pageSize", PageSize);  // 한 페이지당 문진표 개수

	    ModelAndView mav = new ModelAndView();
	    mav.setViewName("PatientInfo/List");
	    return mav;
	}
	
		@GetMapping("/Chart/ShowPatient")
		public ModelAndView ChartPatient() {
			List<Patient> patient = new ArrayList<>();
			patient = this.pis.findAll();
			int Count[] = new int[2];
			
			for(Patient p : patient) {
				if(p.getP_Gender().equals("M")) {
					Count[0]++;
				}
				else if(p.getP_Gender().equals("F")) {
					Count[1]++;
				}
			}
			
			ModelAndView mav = new ModelAndView();
			mav.addObject("Count", Count);
			mav.setViewName("Chart/ShowPatient");
			return mav;
		}

	
//		@GetMapping("/PatientInfo/List") // 문진표 목록 이동
//		public ModelAndView PatientInfoList(@RequestParam(name="page", required=false)Integer page, 
//										HttpSession session, Model model) {
//		final int PageSize = 20;
//		String UserId = (String) session.getAttribute("UserId");
//		UserInfoDTO uiDTO = this.uis.UserInfoRead(UserId);
//		model.addAttribute("UserId", UserId);
//		model.addAttribute("UserName", uiDTO.getUserName());
//
//		
////		List<Patient> PatientInfoList = new ArrayList<Patient>();
////		PatientInfoList = this.pis.PatientInfoList(UserId);
////		
//		List<PatientInfoListResponseDTO> PatientInfoList = this.pis.PatientListPage(page, PageSize, UserId);
//		
//		ModelAndView mav = new ModelAndView();
//		// 페이지네이션 정보
//	    int totalPages;
//	    long totalElements;
//	    int currentPage; // 현재 페이지 번호
//
//	    // 모델에 페이지네이션 정보 추가
////	    model.addAttribute("PatientInfoList", PatientInfoList);
////	    model.addAttribute("totalPages", totalPages);
////	    model.addAttribute("totalElements", totalElements);
////	    model.addAttribute("currentPage", currentPage);
////	    model.addAttribute("pageSize", PageSize);
//		mav.addObject("PatientInfoList", PatientInfoList);
//		mav.addObject(mav)
//		mav.setViewName("PatientInfo/List");
//		return mav;
//	}
	
//	@GetMapping("/PatientInfo/List") // 문진표 목록 이동
//	public ModelAndView PatientInfoList(@RequestParam(name="page", required=false)Integer page, 
//										HttpSession session, Model model) {
//		final int PageSize = 5;
//		String UserId = (String) session.getAttribute("UserId");
//		UserInfoDTO uiDTO = this.uis.UserInfoRead(UserId);
//		model.addAttribute("UserId", UserId);
//		model.addAttribute("UserName", uiDTO.getUserName());
//
//		
////		List<Patient> PatientInfoList = new ArrayList<Patient>();
////		PatientInfoList = this.pis.PatientInfoList(UserId);
//		
//		ModelAndView mav = new ModelAndView();
//		List<PatientInfoListResponseDTO> PatientInfoList = this.pis.PatientListPage(page, page)
//		mav.addObject("PatientInfoList", PatientInfoList);
//		mav.setViewName("PatientInfo/List");
//		return mav;
//	}

	@GetMapping("/PatientInfo/Delete") // 문진표 삭제
	public String PatientInfoDelete(@RequestParam("P_Id") Integer P_Id, Model model) {
		this.pis.PatientInfoDelete(P_Id);
		model.addAttribute("message", "문진표가 삭제되었습니다.");
		return "redirect:/PatientInfo/List";
	}

}