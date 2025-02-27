package Hospital.Patient.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class D_HospitalListDTO { // DB 칼럼과 동일한 명으로 변수명 통일
	private Integer H_Id;
	private String H_Region;
	private String H_Name;
	private String H_Department;
	private Integer Bed_Total;
	private Integer Bed_General;
	private Integer Bed_Psy;
	private Integer Bed_Nursing;
	private Integer Bed_Intensive;
	private Integer Bed_Isolation;
	private Integer Bed_Clean;
	private String H_Phone_Number;
	private String H_Address;
	
}
