package Hospital.Appointment.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Hospital.Appointment.DTO.AppointmentDTO;
import Hospital.Appointment.DTO.AppointmentMapper;
import Hospital.Appointment.DTO.AppointmentReadDTO;
import Hospital.Appointment.Entity.Appointment;
import Hospital.Appointment.Entity.AppointmentRepository;
import Hospital.Patient.Entity.D_Hospital;
import Hospital.Patient.Entity.Patient;
import Hospital.User.Entity.UserInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Service
public class AppointmentService {
    @Autowired
    private AppointmentRepository apr;

    public List<Appointment> findAll(){
		   return this.apr.findAll();
	   }
    @PersistenceContext
    private EntityManager entityManager; // EntityManager 직접 사용

    // 예약 생성
    @Transactional
    public AppointmentDTO createAppointment(AppointmentDTO dto) {
        UserInfo user = entityManager.find(UserInfo.class, dto.getUserId());
        if (user == null) {
            throw new IllegalArgumentException("해당 사용자 없음: " + dto.getUserId());
        }

        D_Hospital hospital = entityManager.find(D_Hospital.class, dto.getHospitalId());
        if (hospital == null) {
            throw new IllegalArgumentException("해당 병원 없음: " + dto.getHospitalId());
        }

        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointment.setHospital(hospital);
        appointment.setPatientName(dto.getPatientName());
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setCreatedTime(LocalDateTime.now());

        entityManager.persist(appointment); // 엔터티 저장
        entityManager.flush(); // 🔥 즉시 DB에 반영하여 ID 생성
        return AppointmentMapper.toDTO(appointment);
    }
    
    public AppointmentReadDTO AppointmentRead(Long Id) throws NoSuchElementException{
		   Appointment appointment = this.apr.findById(Id).orElseThrow();
		   return AppointmentReadDTO.AppointmentFactory(appointment);
	}
    
    public void AppointmentDelete(Long Id) {
		this.apr.deleteById(Id);
	}
    
	
}