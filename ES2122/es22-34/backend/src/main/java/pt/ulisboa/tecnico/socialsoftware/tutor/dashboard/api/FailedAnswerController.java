package pt.ulisboa.tecnico.socialsoftware.tutor.dashboard.api;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;

import pt.ulisboa.tecnico.socialsoftware.tutor.dashboard.FailedAnswerService;
import pt.ulisboa.tecnico.socialsoftware.tutor.dashboard.dto.FailedAnswerDto;


@RestController
public class FailedAnswerController {

    private static final Logger logger = LoggerFactory.getLogger(FailedAnswerController.class);

    @Autowired
    private FailedAnswerService failedAnswerService;

    @GetMapping("/failedAnswers/dashboard/{dashboardId}")
    @PreAuthorize("hasRole('ROLE_STUDENT') and hasPermission(#dashboardId, 'DASHBOARD.ACCESS')")
    public List<FailedAnswerDto> getFailedAnswers(@PathVariable int dashboardId) {
        return failedAnswerService.getFailedAnswers(dashboardId);
    }

    @PutMapping("/students/dashboard/{dashboardId}/failedAnswers")
    @PreAuthorize("hasRole('ROLE_STUDENT') and hasPermission(#dashboardId, 'DASHBOARD.ACCESS')")
    public List<FailedAnswerDto> updateFailedAnswers(@PathVariable int dashboardId, @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) {
        return failedAnswerService.updateFailedAnswers(dashboardId, startDate, endDate);
    }

    @DeleteMapping("/failedAnswers/{failedAnswerId}/delete")
    @PreAuthorize("hasRole('ROLE_STUDENT') and hasPermission(#failedAnswerId, 'FAILED_ANSWER.ACCESS')")
    public void removeFailedAnswer(@PathVariable int failedAnswerId) {
        failedAnswerService.removeFailedAnswer(failedAnswerId);
    }

}
