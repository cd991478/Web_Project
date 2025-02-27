package Hospital.Error.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import Hospital.Error.DTO.ErrorLogDTO;
import Hospital.Error.Entity.ErrorLog;
import Hospital.Error.Entity.ErrorLogRepository;



@Service
public class ErrorLogService {

    @Autowired
    private ErrorLogRepository errorLogRepository;

    // 에러 로그를 데이터베이스에 저장
    public void saveErrorLog(ErrorLogDTO errorLogDTO) {
        ErrorLog errorLog = new ErrorLog();
        errorLog.setErrorMessage(errorLogDTO.getErrorMessage());
        errorLog.setExceptionType(errorLogDTO.getExceptionType());
        errorLog.setDetails(errorLogDTO.getDetails());
        errorLog.setTimestamp(errorLogDTO.getTimestamp());

        errorLogRepository.save(errorLog);  // 데이터베이스에 저장
    }
}