package Hospital.Error.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Hospital.Error.Entity.ErrorLogRepository;

@Service
public class ErrorLogAnalysisService {

    @Autowired
    private ErrorLogRepository errorLogRepository;

    public List<Object[]> countErrorsByDateRange(String startDate, String endDate) {
        try {
            // 날짜 형식 검증 추가
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate start = LocalDate.parse(startDate, formatter);
            LocalDate end = LocalDate.parse(endDate, formatter);
            
            // 데이터베이스 호출
            return errorLogRepository.countErrorsByDateRange(start, end);
        } catch (Exception e) {
            // 예외 처리 및 로그
            e.printStackTrace();  // 콘솔에 출력
            throw new RuntimeException("Error processing date range", e);  // 예외 재발생
        }
    }
}

