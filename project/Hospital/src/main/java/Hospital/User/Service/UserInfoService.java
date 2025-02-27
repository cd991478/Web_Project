package Hospital.User.Service;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Hospital.Appointment.Service.AppointmentService;
import Hospital.Patient.Entity.Patient;
import Hospital.Patient.Entity.PatientInfoRepository;
import Hospital.Patient.Service.D_HospitalService;
import Hospital.Patient.Service.PatientInfoService;
import Hospital.User.DTO.UserInfoCreateDTO;
import Hospital.User.DTO.UserInfoDTO;
import Hospital.User.DTO.UserInfoEditDTO;
import Hospital.User.Entity.UserInfo;
import Hospital.User.Entity.UserInfoRepository;

@Service
public class UserInfoService {
	
	@Autowired
	private UserInfoRepository uir;
	
	@Autowired
	private PatientInfoService pis;
	
	@Autowired
	private PatientInfoRepository pir;
	
	@Autowired
	private D_HospitalService d_hs;
	
	@Autowired
	private AppointmentService as;
	
	public List<UserInfo> findAll(){
		return this.uir.findAll();
	}
	
	//로그인 실행 로직
	public boolean UserLogin(String UserId, String UserPw) throws Exception{
		UserInfo userinfo = this.uir.findById(UserId).orElse(null);
		if(userinfo==null) {
			return false;
		}
		else if(!userinfo.getUserPw().equals(Hashing(UserPw.getBytes(), userinfo.getSalt()))) {
			return false;
		}
		return true;
	}
	
	//회원 가입 로직 (재출시 DB에 등록)
	public void UserInfoRegister(UserInfoCreateDTO uicDTO) throws Exception {
		UserInfo userinfo = UserInfo.builder()
					.UserId(uicDTO.getUserId())
					.UserPw(Hashing(uicDTO.getUserPw().getBytes(), uicDTO.getSalt()))
					.UserName(uicDTO.getUserName())
					.UserRegNum(uicDTO.getUserRegNum())
					.UserGender(uicDTO.getUserGender())
					.UserPhone(uicDTO.getUserPhone())
					.UserAddress1(uicDTO.getUserAddress1())
					.UserAddress2(uicDTO.getUserAddress2())
					.Salt(uicDTO.getSalt())
					.build();
		this.uir.save(userinfo); // 회원 정보 저장
	}
	
	//회원 가입 아이디 중복 확인 버튼 로직
	public boolean UserIdCheck(String UserId) {
		return this.uir.existsById(UserId);
	}
	
	//회원정보 수정 로직
	public void UserInfoModify(UserInfoEditDTO uieDTO) {
		UserInfo userinfo = this.uir.findById(uieDTO.getUserId()).orElseThrow();
		userinfo = uieDTO.Fill(userinfo);
		this.uir.save(userinfo);
	}
	
	//회원정보 읽기 로직
	public UserInfoDTO UserInfoRead(String UserId) {
		UserInfo userinfo = this.uir.findById(UserId).orElseThrow();
		return UserInfoDTO.UserInfoFactory(userinfo);
	}
	
	//아이디 찾기 로직
	public UserInfoDTO FindUserId(String UserName, String UserRegNum) {
		List<UserInfo> userinfolist = new ArrayList<UserInfo>();
		UserInfo userinforesult;
		userinfolist = this.uir.findAll();
		UserInfoDTO uiDTO = new UserInfoDTO();
		for(UserInfo userinfo: userinfolist) {
			if((userinfo.getUserName().equals(UserName))&&(userinfo.getUserRegNum().equals(UserRegNum))) {
				userinforesult = userinfo;
				return UserInfoDTO.UserInfoFactory(userinforesult);
			}
		}
		
		return uiDTO = null;
	}
	
	//비번 찾기기능 로직
	public UserInfoDTO FindUserPw(String UserId, String UserName, String UserRegNum) {
		
		UserInfo userinfo = this.uir.findById(UserId).orElse(null);
		UserInfoDTO uiDTO = new UserInfoDTO();
		if(userinfo != null) {
			if((userinfo.getUserName().equals(UserName)) && (userinfo.getUserRegNum().equals(UserRegNum))) {
				return UserInfoDTO.UserInfoFactory(userinfo);
			}
			return uiDTO = null;
		}
		else {
		
			return uiDTO = null;
		}
	}
	
	public void ResetUserPw(String UserId, String UserPw) throws Exception { // 비밀번호 재설정 로직
		UserInfo userinfo = this.uir.findById(UserId).orElseThrow();
		userinfo.setUserPw(Hashing(UserPw.getBytes(), userinfo.getSalt()));
		this.uir.save(userinfo);
	}
	
	public boolean DeleteUserId(String UserId, String UserPw) throws Exception { // 회원 탈퇴 로직
		UserInfo userinfo = this.uir.findById(UserId).orElseThrow();
		//외래키 조건으로 인해 회원 정보에서의 Id를 외래키로 사용하는 Appointment를 먼저 삭제해야 한다.
		List<Patient> allpatient = new ArrayList<Patient>();
		List<Patient> patient = new ArrayList<Patient>();
		allpatient = this.pis.findAll();
		System.out.println(allpatient);
		if((userinfo.getUserPw().equals(Hashing(UserPw.getBytes(), userinfo.getSalt())))) { // 해당 유저의 문진표 삭제
			for(Patient patients: allpatient) {
				if(UserId!=null && patients.getP_UserId().equals(UserId)){
					Integer p_id = patients.getP_Id();
					this.pir.deleteById(p_id); // 문진표 삭제 (번호로)
				}
			}
/////////////////// 회원 탈퇴시 해당 회원 예약들의 상태칼럼을 (status) '탈퇴'로 변경하도록 추후 변경
			
			this.uir.delete(userinfo); // 회원 삭제
			return true;
		}
		return false;
	}
	
	public String Byte_to_String(byte[] temp) {
		StringBuilder sb = new StringBuilder();
		for(byte a: temp) {
			sb.append(String.format("%02x", a)); // %02x는 두 자릿수의 16진수로 표현하는 스트링 포맷
		}
		return sb.toString();
	}
	
	public String Hashing(byte[] password, String salt) throws Exception{//해싱 함수: 임의의 길이값을 받아 고정된 길이의 값으로 반환
		MessageDigest md = MessageDigest.getInstance("SHA-256"); 
		for(int i = 0; i < 10000; i++) {
			String temp = Byte_to_String(password)+salt;//입력바다은 
			md.update(temp.getBytes());
			password = md.digest();
		}
		return Byte_to_String(password);
	}

	
}
