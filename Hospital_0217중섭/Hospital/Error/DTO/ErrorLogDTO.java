package Hospital.Error.DTO;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorLogDTO {

    private String errorMessage;
    private String exceptionType;
    private String details;
    private LocalDateTime timestamp;

    // Constructor
    public ErrorLogDTO(String errorMessage, String exceptionType, String details, LocalDateTime timestamp) {
        this.errorMessage = errorMessage;
        this.exceptionType = exceptionType;
        this.details = details;
        this.timestamp = timestamp;
    }
}
