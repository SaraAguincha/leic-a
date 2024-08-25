package pt.ulisboa.tecnico.socialsoftware.tutor.dashboard.api;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PutMapping;

import pt.ulisboa.tecnico.socialsoftware.tutor.dashboard.DifficultQuestionService;
import pt.ulisboa.tecnico.socialsoftware.tutor.dashboard.dto.DifficultQuestionDto;

@RestController
public class DifficultQuestionController {

    private static final Logger logger = LoggerFactory.getLogger(DifficultQuestionController.class);

    @Autowired
    private DifficultQuestionService difficultQuestionService;

    DifficultQuestionController(DifficultQuestionService difficultQuestionService){
        this.difficultQuestionService = difficultQuestionService;
    }

    @GetMapping("/difficultQuestions/dashboard/{dashboardId}")
    @PreAuthorize("hasRole('ROLE_STUDENT') and hasPermission(#dashboardId, 'DASHBOARD.ACCESS')")
    public List<DifficultQuestionDto> getDifficultQuestions(@PathVariable int dashboardId) {
        return difficultQuestionService.getDifficultQuestions(dashboardId);
    }

    @DeleteMapping("/difficultQuestions/{difficultQuestionId}/delete")
    @PreAuthorize("hasRole('ROLE_STUDENT') and hasPermission(#difficultQuestionId, 'DIFFICULT_QUESTIONS.ACCESS')")
    public void removeDifficultQuestion(@PathVariable int difficultQuestionId){
        difficultQuestionService.removeDifficultQuestion(difficultQuestionId);
    }
    @PutMapping("/difficultQuestions/dashboard/{dashboardId}")
    @PreAuthorize("hasRole('ROLE_STUDENT') and hasPermission(#dashboardId, 'DASHBOARD.ACCESS')")
    public void updateDifficultQuestions(@PathVariable int dashboardId) {
        difficultQuestionService.updateDifficultQuestions(dashboardId);
    }
}