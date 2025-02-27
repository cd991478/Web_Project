package Hospital.Appointment.Service;  // 패키지는 기존 서비스 클래스와 동일하게

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import Hospital.Appointment.DTO.AppointmentDTO;

@Service
public class AppointmentAsyncService {
    
    private final AppointmentService appointmentService;

    public AppointmentAsyncService(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @Async("appointmentExecutor") // AsyncConfig.java에서 설정한 스레드 풀 사용
    public void processAppointment(AppointmentDTO apDTO) {
        System.out.println(Thread.currentThread().getName() + "에서 예약 처리 중...");
        
        appointmentService.createAppointment(apDTO); // 실제 예약 처리
        
        System.out.println("예약 처리가 완료되었습니다.");
    }
}
