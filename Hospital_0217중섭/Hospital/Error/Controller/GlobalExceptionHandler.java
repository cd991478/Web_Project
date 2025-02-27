package Hospital.Error.Controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


import Hospital.Error.DTO.ErrorLogDTO;
import Hospital.Error.Service.ErrorLogService;

@ControllerAdvice

public class GlobalExceptionHandler {

  
	@Autowired
    private ErrorLogService errorLogService;

    @ExceptionHandler(Exception.class)  // 모든 예외 처리
     //@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorLogDTO handleException(Exception ex) {
        // 에러 정보를 DTO로 변환
        ErrorLogDTO errorLogDTO = new ErrorLogDTO(
                ex.getMessage(),
                ex.getClass().getName(),
                ex.toString(),
                LocalDateTime.now()
        );

        // 에러 정보를 데이터베이스에 저장
        errorLogService.saveErrorLog(errorLogDTO);

        // 클라이언트에 DTO 형태로 에러 반환
        return errorLogDTO;
    }
    
   
}