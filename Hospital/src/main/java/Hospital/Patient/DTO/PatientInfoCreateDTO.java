package Hospital.Patient.DTO;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class PatientInfoCreateDTO {
	//DTO(Data Transfer Object)는 값을 담는 컨테이너 객체이다.
	//책 입력화면에서 제목과 가격을 입력했을 때 클라이언트가 서버로 전달한 값들을 담는 역할
	//본문에서 DTO는 클라이언트의 HTTP 파라미터를 담는 컨테이너 객체로 사용된다. 
	//DTO 객체를 따로 생성하면 나중에 데이터베이스는 그대로이지만 화면 UI가 변경되는 경우 대처하기가 쉬워진다.
	//데이터의 무결성을 보장하기 위한 절차적인 방법. 

	
	//@Getter와 @Setter는 롬복의 어노테이션으로 @Data 어노테이션의 하위집합이다
	//각각 자바 빈즈의 getter와 setter를 자동으로 만들어준다.
	//본 페이지에서는 toString과 hashcode 등의 메소드를 사용하지 않으므로
	//간단하게 @Getter와 @Setter만을 사용하기로 한다.
	   @NonNull
	   private String Name;
	   @NonNull
	   private String Gender;
	   @NonNull
	   private Integer Age;
	   @NonNull
	   private String Phone_Number;
	   @NonNull
	   private String Address_1;
	   @NonNull
	   private String Address_2;
	   @NonNull
	   private Integer Taking_Pill;
	   @NonNull
	   private Integer Nose;
	   @NonNull
	   private Integer Cough;
	   @NonNull
	   private Integer Pain;
	   @NonNull
	   private Integer Diarrhea;
	   @NonNull
	   private String High_Risk_Group;
	   @NonNull
	   private Integer VAS;
	   @NonNull
	   private Integer Agreement;
}
