package Hospital.User.DTO;

import java.time.LocalDateTime;

import Hospital.User.Entity.UserInfo;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInfoDTO {
	
	@Id
	private String UserId;
	
	private String UserPw;
	
	private String UserName;
	
	private String UserRegNum;
	
	private String UserGender;
	
	private String UserPhone;
	
	private String UserAddress1;
	
	private String UserAddress2;
	
	private LocalDateTime InsertDateTime;
	
	private String Salt;
	
	public UserInfoDTO FromUserInfo(UserInfo u) {
		this.UserId = u.getUserId();
		this.UserPw = u.getUserPw();
		this.UserName = u.getUserName();
		this.UserRegNum = u.getUserRegNum();
		this.UserGender = u.getUserGender();
		this.UserPhone = u.getUserPhone();
		this.UserAddress1 = u.getUserAddress1();
		this.UserAddress2 = u.getUserAddress2();
		this.InsertDateTime = u.getInsertDateTime();
		this.Salt = u.getSalt();
		return this;
	}
	
	public static UserInfoDTO UserInfoFactory(UserInfo u) {
		UserInfoDTO uiDTO = new UserInfoDTO();
		uiDTO.FromUserInfo(u);
		return uiDTO;
	}
}
