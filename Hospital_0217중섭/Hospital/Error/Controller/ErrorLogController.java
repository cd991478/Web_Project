package Hospital.Error.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import Hospital.Error.Service.ErrorLogAnalysisService;


@RestController
public class ErrorLogController {

    @Autowired
    private ErrorLogAnalysisService errorLogAnalysisService;

    @GetMapping("/error-count-by-date")
    public ModelAndView getErrorCountByDateRange(
            @RequestParam String startDate,
            @RequestParam String endDate) {
        List<Object[]> errorCount = errorLogAnalysisService.countErrorsByDateRange(startDate, endDate);
        ModelAndView modelAndView = new ModelAndView("error-count-by-date");  // 뷰 템플릿 이름
        modelAndView.addObject("errorCount", errorCount);  // 모델에 데이터 추가
        return modelAndView;
    }
}
