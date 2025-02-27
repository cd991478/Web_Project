package Hospital.Error.Entity;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ErrorLogRepository extends JpaRepository<ErrorLog, Long> {
	 // 에러 타입별 카운트를 위한 JPQL 쿼리
    @Query("SELECT e.exceptionType, COUNT(e) FROM ErrorLog e GROUP BY e.exceptionType")
    List<Object[]> countErrorByType();
    
    @Query("SELECT e.exceptionType, COUNT(e) FROM ErrorLog e WHERE e.timestamp BETWEEN :startDate AND :endDate GROUP BY e.exceptionType")
    List<Object[]> countErrorsByDateRange(LocalDate start, LocalDate end);
    
    
}