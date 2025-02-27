package Hospital.User.DTO;

import java.security.SecureRandom;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class UserInfoCreateDTO {
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
	@NonNull
	private String Salt;
	
	private UserInfoCreateDTO() {
	     this.Salt = generateSalt(); // 아래의 generateSalt() 자체가 결괏값으로 16자리의 스트링을 반환한다.
	     System.out.println(Salt);
	}//DTO 초기 생성시 Salt를 생성하여 멤버변수 Salt에 대입한다.
	 //getSalt()를 메서드 이름으로 쓰려고 했으나 @Getter를 사용하면서 두 메서드 간 충돌이 발생하여 generateSalt()로 변경

	 private String generateSalt() {
	     SecureRandom random = new SecureRandom(); 
	     byte[] salt = new byte[16];
	     random.nextBytes(salt);
	     return Byte_to_String(salt);
	 }
	 //16개의 정보를 저장하는 바이트 배열에서 자바가 제공하는 강화된 보안의 난수 생성기로 배열에 넣은 후 String으로 변환한다.
	//이때 String으로 변환하는 함수는 .toString()이 아닌 Byte_to_String 함수를 사용한다(DTO자체에 내장시킴)

	 private String Byte_to_String(byte[] temp) {
		StringBuilder sb = new StringBuilder();
		for(byte a: temp) {
			sb.append(String.format("%02x", a)); //x는 16진수를 뜻하고 02는 두 자릿수의 16진수를 말한다.
		}
		return sb.toString();
	}//StringBuilder를 사용하여 String 자료형을 원하는 대로 편집할 수 있도록 한다. for each 반복문으로 String의 format을 "%02x"로 지정
}
