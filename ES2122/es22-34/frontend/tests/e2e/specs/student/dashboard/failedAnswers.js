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
            'Quiz Title',
            'Question Title',
            'Question Title2'
        );
        cy.contains('Logout').click();
    });

    it('student updates and deletes failed answers', () => {
        cy.demoStudentLogin();
        cy.solveQuizzFailedAnswers('Quiz Title', 2);
        cy.updateAndDeleteFailedAnswers();
        cy.contains('Logout').click();
        Cypress.on('uncaught:exception', (err, runnable) => {
          // returning false here prevents Cypress from
          // failing the test
          return false;
        });
    });
  
    afterEach(() => {
        cy.deleteFailedAnswers();
        cy.deleteQuestionsAndAnswers();
    });
});