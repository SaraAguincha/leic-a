describe('Student Walkthrough', () => {
    beforeEach(() => {
        cy.deleteQuestionsAndAnswers();
        //create quiz
        cy.demoTeacherLogin();
        cy.createQuestion(
            'Question Title',
            'Question',
            'Option',
            'Option',
            'ChooseThisWrong',
            'Correct'
        );
        cy.createQuestion(
            'Question Title2',
            'Question',
            'Option',
            'Option',
            'ChooseThisWrong',
            'Correct'
        );
        cy.createQuizzWith2Questions(
            'Weekly Score Title',
            'Question Title',
            'Question Title2'
        );
        cy.contains('Logout').click();
    });

    it('student updates and deletes weekly scores', () => {
        cy.demoStudentLogin();
        cy.solveQuizzWeeklyScores('Weekly Score Title', 2);
        cy.updateAndDeleteWeeklyScores(); 
        cy.contains('Logout').click();
        Cypress.on('uncaught:exception', (err, runnable) => {
            // returning false here prevents Cypress from
            // failing the test
            return false;
        });
        cy.deleteWeeklyScores();
    });

    afterEach(() => {
        cy.deleteWeeklyScores();
        cy.deleteQuestionsAndAnswers();
    });
});