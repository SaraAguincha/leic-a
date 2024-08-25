describe('Student Walkthrough', () => {
    beforeEach(() => {
        //create quiz
        cy.demoTeacherLogin();
        cy.createQuestion(
            'Question Title',
            'Question',
            'Option',
            'ChooseThisWrong',
            'Option',
            'Correct'
        );
        cy.createQuestion(
            'Question Title2',
            'Question',
            'Option',
            'ChooseThisWrong',
            'Option',
            'Correct'
        );
        cy.createQuizzWith2Questions(
            'Quiz Title',
            'Question Title',
            'Question Title2'
        );
        cy.contains('Logout').click();
    });

    it('student updates and deletes difficult question', () => {
        cy.demoStudentLogin();
        cy.solveQuizzDifficultQuestions('Quiz Title', 2);
        cy.updateAndDeleteDifficultQuestions();
        cy.contains('Logout').click();
        Cypress.on('uncaught:exception', (err, runnable) => {
            // returning false here prevents Cypress from
            // failing the test
            return false;
        });
    });

    afterEach(() => {
        cy.deleteDifficultQuestions();
        cy.deleteQuestionsAndAnswers();
    });
});