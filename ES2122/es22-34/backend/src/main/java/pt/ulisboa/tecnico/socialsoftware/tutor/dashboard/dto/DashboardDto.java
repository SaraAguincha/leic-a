package pt.ulisboa.tecnico.socialsoftware.tutor.dashboard.dto;

import pt.ulisboa.tecnico.socialsoftware.tutor.dashboard.domain.Dashboard;

import java.time.LocalDateTime;


public class DashboardDto {
    private Integer id;
    private String lastCheckFailedAnswers;
    private String lastCheckDifficultQuestions;
    private String lastCheckWeeklyScores;

    public DashboardDto(Dashboard dashboard) {
        id = dashboard.getId();
        lastCheckFailedAnswers = dashboard.getLastCheckFailedAnswers() == null ? null : LocalDateTime.parse(dashboard.getLastCheckFailedAnswers().toString()).toString();
        lastCheckDifficultQuestions = dashboard.getLastCheckDifficultQuestions() == null ? null : LocalDateTime.parse(dashboard.getLastCheckDifficultQuestions().toString()).toString();
        lastCheckWeeklyScores = dashboard.getLastCheckWeeklyScores() == null ? null :  LocalDateTime.parse(dashboard.getLastCheckWeeklyScores().toString()).toString();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastCheckFailedAnswers() {
        return lastCheckFailedAnswers;
    }

    public void setLastCheckFailedAnswers(String lastCheckFailedAnswers) {
        this.lastCheckFailedAnswers = lastCheckFailedAnswers;
    }

    public String getLastCheckDifficultQuestions() {
        return lastCheckDifficultQuestions;
    }

    public void setLastCheckDifficultQuestions(String lastCheckDifficultQuestions) {
        this.lastCheckDifficultQuestions = lastCheckDifficultQuestions;
    }

    public String getLastCheckWeeklyScores() {
        return lastCheckWeeklyScores;
    }

    public void setLastCheckWeeklyScores(String lastCheckWeeklyScores) {
        this.lastCheckWeeklyScores = lastCheckWeeklyScores;
    }

    @Override
    public String toString() {
        return "DashboardDto{" +
                "id=" + id +
                ", lastCheckFailedAnswers=" + lastCheckFailedAnswers +
                ", lastCheckDifficultAnswers=" + lastCheckDifficultQuestions +
                ", lastWeeklyStats=" + lastCheckWeeklyScores +
                "}";
    }
}
