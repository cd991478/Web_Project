package Hospital.User.DTO;

import Hospital.User.Entity.UserInfo;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class UserInfoEditDTO {
	
	@NonNull
	private String UserId;
	@NonNull
	private String UserPw;
	@NonNull
	private String UserName;
	@NonNull
	private String UserRegNum;
	@NonNull
	private String UserGender;
	@NonNull
	private String UserPhone;
	@NonNull
	private String UserAddress1;
	@NonNull
	private String UserAddress2;
	
	public UserInfo Fill(UserInfo u) {
		u.setUserPw(this.UserPw);
		u.setUserName(this.UserName);
		u.setUserRegNum(this.UserRegNum);
		u.setUserGender(this.UserGender);
		u.setUserPhone(this.UserPhone);
		u.setUserAddress1(this.UserAddress1);
		u.setUserAddress2(this.UserAddress2);
		return u;
	}
}
