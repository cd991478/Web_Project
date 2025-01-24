package Hospital.Login.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import Hospital.Login.DTO.UserInfoCreateDTO;
import Hospital.Login.Service.UserLoginService;
import jakarta.servlet.http.HttpSession;

@Controller
public class UserLoginController {

	@Autowired
	private UserLoginService uls;
	
	@GetMapping("/Home") // 홈 화면 출력
	public String GotoHome(HttpSession session, Model model) {
		String UserId = (String)session.getAttribute("UserId");
		if(UserId != null) {
			model.addAttribute("SessionUser",UserId);
		}
		return "/Home";
	}
	
	@GetMapping("/Login") // 로그인 화면으로 이동
	public String GotoLogin() {
		return "/Login";
	}
	
	@PostMapping("/Login") // 로그인 버튼 누르면 실행 함수
	public String Login(@RequestParam("UserId") String UserId,
						@RequestParam("UserPw") String UserPw,
						HttpSession session, Model model) {
		boolean LoginSuccess = this.uls.UserLogin(UserId,UserPw);
		
		if(LoginSuccess) {
			session.setAttribute("UserId", UserId);
			return "redirect:/Home";
		}
		else {
			model.addAttribute("errorMessage", "아이디 또는 비밀번호가 틀렸습니다.");
			return "/Login";
		}
	}
	
	
	
	@GetMapping("/SignUp") // 회원가입 버튼을 누르면 회원가입 화면으로 이동 
	public String GotoSignUp() {
		return "/SignUp";
	}
	
	@PostMapping("/SignUp") // 회원가입 화면에서, 입력 후 회원가입 완료 누르면 실행 후 이동
	public String SignUp(UserInfoCreateDTO uicDTO) {
		this.uls.UserInfoRegister(uicDTO);
		return "/SignUpComplete";
	}
	
	@GetMapping("/Logout") // 로그아웃
	public String Logout(HttpSession session) {
		session.invalidate();
		return "redirect:/Home";
	}
	
	@GetMapping("/UserInfo") // 회원정보 수정 기능, 추후 작업
	public String UserInfo() {
		return "/UserInfo";
	}
	
	
	
}
