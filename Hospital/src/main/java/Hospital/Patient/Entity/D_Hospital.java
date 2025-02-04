package Hospital.Patient.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="d_hospital")

public class D_Hospital {
	@Id
	private Integer H_Id;
	@Column(name = "H_Region")
	private String H_Region;
	@Column(name = "H_Name")
	private String H_Name;
	@Column(name = "H_Department")
	private String H_Department;
	@Column(name = "Bed_Total")
	private Integer Bed_Total;
	@Column(name = "Bed_General")
	private Integer Bed_General;
	@Column(name = "Bed_Psy")
	private Integer Bed_Psy;
	@Column(name = "Bed_Nursing")
	private Integer Bed_Nursing;
	@Column(name = "Bed_Intensive")
	private Integer Bed_Intensive;
	@Column(name = "Bed_Isolation")
	private Integer Bed_Isolation;
	@Column(name = "Bed_Clean")
	private Integer Bed_Clean;
	@Column(name = "H_Phone_Number")
	private String H_Phone_Number;
	@Column(name = "H_Address")
	private String H_Address;	
}