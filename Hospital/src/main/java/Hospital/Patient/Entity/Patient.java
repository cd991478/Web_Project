package Hospital.Patient.Entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Integer P_Id;
	   @Column(name="p_user_id")
	   private String P_UserId;
	   @Column
	   private String P_Name;
	   @Column   
	   private String P_Gender;
	   @Column
	   private Integer P_RegNum;
	   @Column
	   private String P_Phone;
	   @Column
	   private String P_Address1;
	   @Column
	   private String P_Address2;
	   @Column
	   private Integer P_TakingPill;
	   @Column
	   private Integer P_Nose;
	   @Column
	   private Integer P_Cough;
	   @Column
	   private Integer P_Pain;
	   @Column
	   private Integer P_Diarrhea;
	   @Column
	   private String P_HighRiskGroup;
	   @Column
	   private Integer P_VAS;
	   @Column
	   private Integer P_Agreement;
	      
	   @CreationTimestamp
	   @Column(name="p_insert_date_time")
	   private LocalDateTime P_InsertDateTime;   

}