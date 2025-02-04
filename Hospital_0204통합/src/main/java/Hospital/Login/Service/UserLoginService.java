package Hospital.Login.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Hospital.Login.DTO.UserInfoCreateDTO;
import Hospital.Login.Entity.UserInfo;
import Hospital.Login.Entity.UserInfoRepository;

@Service
public class UserLoginService {
	
	@Autowired
	private UserInfoRepository uir;
	
	//로그인 실행 로직
	public String UserLogin(String UserId, String UserPw){
		UserInfo userinfo = this.uir.findById(UserId).orElse(null);
		if(userinfo==null) {
			return "IdError";
		}
		else if(!userinfo.getUserPw().equals(UserPw)) {
			return "PwError";
		}
		return "Success";
	}
	
	//회원 가입 로직 (재출시 DB에 등록)
	public void UserInfoRegister(UserInfoCreateDTO uicDTO) {
		UserInfo userinfo = UserInfo.builder()
					.UserId(uicDTO.getUserId())
					.UserPw(uicDTO.getUserPw())
					.UserName(uicDTO.getUserName())
					.UserRegNum(uicDTO.getUserRegNum())
					.UserGender(uicDTO.getUserGender())
					.UserPhone(uicDTO.getUserPhone())
					.UserAddress1(uicDTO.getUserAddress1())
					.UserAddress2(uicDTO.getUserAddress2())
					.build();
		this.uir.save(userinfo); // 회원 정보 저장
	}
	
	//회원 가입 아이디 중복 확인 버튼 로직
	public boolean UserIdCheck(String UserId) {
		return this.uir.existsById(UserId);
	}
	
	public void UserInfoModify(UserInfoCreateDTO uicDTO) {
		UserInfo userinfo = UserInfo.builder()
				.UserPw(uicDTO.getUserPw())
				.UserName(uicDTO.getUserName())
				.UserRegNum(uicDTO.getUserRegNum())
				.UserGender(uicDTO.getUserGender())
				.UserPhone(uicDTO.getUserPhone())
				.UserAddress1(uicDTO.getUserAddress1())
				.UserAddress2(uicDTO.getUserAddress2())
				.build();
		this.uir.save(userinfo);
	}
	
}
