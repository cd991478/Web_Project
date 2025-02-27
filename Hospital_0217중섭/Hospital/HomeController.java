package Hospital;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import Hospital.Patient.Entity.D_Hospital;
import Hospital.Patient.Service.D_HospitalService;

@Controller
public class HomeController {
	
	@Autowired
	private D_HospitalService d_hs;
	
	@GetMapping("/AllHospitalList") // 전체 병원 조회
	public ModelAndView GotoAllHospitalList() {
		List<D_Hospital> allhospital = new ArrayList<D_Hospital>();
		allhospital = this.d_hs.FindAllHospital();
		ModelAndView mav = new ModelAndView();
		mav.addObject("AllHospitalList", allhospital);
		mav.setViewName("AllHospitalList");
		return mav;
	}
}
