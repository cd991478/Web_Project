package Hospital.Appointment.Entity;

import java.time.LocalDateTime;

import Hospital.Patient.Entity.D_Hospital;
import Hospital.User.Entity.UserInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Getter
@Setter
public class Appointment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 예약 ID

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserInfo user; // 사용자 (예약자)

    @ManyToOne
    @JoinColumn(name = "H_Id", nullable = false)
    private D_Hospital hospital; // 병원

    @Column
    private String patientName; // 환자 성명

    @Column
    private LocalDateTime appointmentTime; // 예약 시간

    @Column
    private LocalDateTime createdTime; // 예약 생성 시간
}