package Hospital.Login.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Userinfo {
	@Id
	private String UserId;
	@Column
	private String UserPw;
	@Column
	private String UserName;
	@Column
	private String UserRegNum;
	@Column
	private String UserGender;
	@Column
	private String UserPhone;
	@Column
	private String UserAddress1;
	@Column
	private String UserAddress2;
	@CreationTimestamp
	private LocalDateTime InsertDateTime;
}
