package Hospital.User.DTO;

import java.security.MessageDigest;

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
	
	public UserInfo Fill(UserInfo u) throws Exception {
	      u.setUserPw(Hashing(UserPw.getBytes(), u.getSalt()));
	      u.setUserName(this.UserName);
	      u.setUserRegNum(this.UserRegNum);
	      u.setUserGender(this.UserGender);
	      u.setUserPhone(this.UserPhone);
	      u.setUserAddress1(this.UserAddress1);
	      u.setUserAddress2(this.UserAddress2);
	      return u;
	   }
	 private String Byte_to_String(byte[] temp) {
	      StringBuilder sb = new StringBuilder();
	      for(byte a: temp) {
	         sb.append(String.format("%02x", a)); // %02x는 두 자릿수의 16진수로 표현하는 스트링 포맷
	      }
	      return sb.toString();
	   }
	   
	   private String Hashing(byte[] password, String salt) throws Exception{//해싱 함수: 임의의 길이값을 받아 고정된 길이의 값으로 반환
	      MessageDigest md = MessageDigest.getInstance("SHA-256"); 
	      for(int i = 0; i < 10000; i++) {
	         String temp = Byte_to_String(password)+salt;//해당 메서드에서 매개변수로 입력받은 password는 이미 getBytes를 통해 바이트 배열로 변환된 형태이다. 
	         md.update(temp.getBytes());
	         password = md.digest();
	      }
	      return Byte_to_String(password);
	   }

}
