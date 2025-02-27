package Hospital.Patient.Entity;

import org.springframework.data.jpa.repository.JpaRepository;


public interface PatientInfoRepository extends JpaRepository<Patient, Integer> {

//	public Page<Patient> findByP_user_id(String UserId, Pageable pageable); 에러
}
