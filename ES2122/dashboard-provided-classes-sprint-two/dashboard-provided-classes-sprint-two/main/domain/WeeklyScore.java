package pt.ulisboa.tecnico.socialsoftware.tutor.dashboard.domain;


@Entity
public class WeeklyScore implements DomainEntity {


    public void computeStatistics() {
        Set<QuizAnswer> weeklyQuizAnswers = getWeeklyQuizAnswers();

        Set<QuestionAnswer> publicWeeklyQuestionAnswers = weeklyQuizAnswers.stream()
                .filter(quizAnswer -> quizAnswer.canResultsBePublic(dashboard.getCourseExecution().getId()))
                .flatMap(quizAnswer -> quizAnswer.getQuestionAnswers().stream())
                .collect(Collectors.toSet());

        Set<Question> weeklyQuestionsAnswered = publicWeeklyQuestionAnswers.stream()
                .map(QuestionAnswer::getQuizQuestion)
                .map(QuizQuestion::getQuestion).collect(Collectors.toSet());

        setNumberAnswered((int) weeklyQuestionsAnswered.stream()
                .map(Question::getId).count());
        setUniquelyAnswered((int) weeklyQuestionsAnswered.stream()
                .map(Question::getId).distinct().count());
        setPercentageCorrect(publicWeeklyQuestionAnswers.size() > 0 ? (int) Math.round((publicWeeklyQuestionAnswers.stream().filter(QuestionAnswer::isCorrect).count() /
                (double) publicWeeklyQuestionAnswers.size()) * 100.0) : 0);

        if (DateHandler.now().isAfter(week.plusDays(7).atStartOfDay())) {
            closed = weeklyQuizAnswers.stream()
                    .noneMatch(quizAnswer -> !quizAnswer.canResultsBePublic(dashboard.getCourseExecution().getId()));
        }
    }

    private Set<QuizAnswer> getWeeklyQuizAnswers() {
        return dashboard.getStudent().getQuizAnswers().stream()
                .filter(quizAnswer -> quizAnswer.getQuiz().getCourseExecution() == dashboard.getCourseExecution())
                .filter(this::isAnswerWithinWeek)
                .collect(Collectors.toSet());
    }

    private boolean isAnswerWithinWeek(QuizAnswer quizAnswer) {
        TemporalAdjuster weekSaturday = TemporalAdjusters.nextOrSame(DayOfWeek.SATURDAY);

        LocalDate answerDate = quizAnswer.getAnswerDate().toLocalDate();
        return (answerDate.isEqual(this.week) || answerDate.isEqual(this.week.with(weekSaturday)) ||
                        (answerDate.isAfter(this.week) && answerDate.isBefore(this.week.with(weekSaturday))));
    }

    public void setClosed(boolean close) {
        TemporalAdjuster weekSunday = TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY);
        LocalDate currentWeek = DateHandler.now().with(weekSunday).toLocalDate();
        if (close && week.isEqual(currentWeek)) {
            throw new TutorException(CANNOT_CLOSE_CURRENT_WEEK, DateHandler.toISOString(week.atStartOfDay()));
        }

        this.closed = close;
    }

}
