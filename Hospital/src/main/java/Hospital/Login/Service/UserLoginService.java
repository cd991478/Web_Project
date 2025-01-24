package Hospital.Login.Service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Hospital.Login.DTO.UserInfoCreateDTO;
import Hospital.Login.Entity.UserInfoRepository;
import Hospital.Login.Entity.Userinfo;


@Service
public class UserLoginService {
	@Autowired
	private UserInfoRepository uir;
	
	//로그인 실행 로직
	public boolean UserLogin(String UserId, String UserPw) throws NoSuchElementException{
		Userinfo u = this.uir.findById(UserId).orElseThrow();
		if(u != null && u.getUserPw().equals(UserPw)) {
			return true;
		}
		return false;
	}
	
	//회원 가입 로직 (재출시 DB에 등록)
	public void UserInfoRegister(UserInfoCreateDTO uicDTO) {
		Userinfo u = Userinfo.builder()
					.UserId(uicDTO.getUserId())
					.UserPw(uicDTO.getUserPw())
					.UserName(uicDTO.getUserName())
					.UserRegNum(uicDTO.getUserRegNum())
					.UserGender(uicDTO.getUserGender())
					.UserPhone(uicDTO.getUserPhone())
					.UserAddress1(uicDTO.getUserAddress1())
					.UserAddress2(uicDTO.getUserAddress2())
					.build();
		this.uir.save(u); // 회원 정보 저장
	}
	
	
	
}
