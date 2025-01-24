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
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
	   @Id
	   @GeneratedValue(strategy = GenerationType.IDENTITY)
	   private Integer PatientId;
	   @Column
	   private String Name;
	   @Column   
	   private String Gender;
	   @Column
	   private Integer Age;
	   @Column
	   private String Phone_Number;
	   @Column
	   private String Address_1;
	   @Column
	   private String Address_2;
	   @Column
	   private Integer Taking_Pill;
	   @Column
	   private Integer Nose;
	   @Column
	   private Integer Cough;
	   @Column
	   private Integer Pain;
	   @Column
	   private Integer Diarrhea;
	   @Column
	   private String High_Risk_Group;
	   @Column
	   private Integer VAS;
	   @Column
	   private Integer Agreement;
	      
	   @CreationTimestamp
	   private LocalDateTime InsertDateTime;   

}