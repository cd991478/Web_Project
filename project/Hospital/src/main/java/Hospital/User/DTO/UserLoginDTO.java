package Hospital.User.DTO;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NonNull;

@Getter
public class UserLoginDTO {
	@Id
	@NonNull
	private String UserId;
	@NonNull
	private String UserPw;
	@NonNull
	private String Salt;//로그인 DTO에 멤버 변수 Salt 추가
}
