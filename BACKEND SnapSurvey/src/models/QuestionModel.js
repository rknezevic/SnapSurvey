const pool = require('../../config/database');


const getQuestionsWithUserAnswers = async (surveyId, userAnswers) => {
    const questions = await pool.query('SELECT * FROM "Question" WHERE "SurveyId" = $1;', [surveyId]);

    const questionsWithAnswers = questions.rows.map(question => {
        const userAnswer = userAnswers.find(answer => answer.QuestionId === question.Id);
        return {
            ...question,
            UserAnswer: userAnswer ? userAnswer.AnswerText : null,
            UserSelectedOptionId: userAnswer ? userAnswer.SelectedOptionId : null
        };
    });

    return questionsWithAnswers;
};

const getQuestionsForSurvey = async (surveyId) => {
    try {
        const questions = await pool.query('SELECT * FROM "Question" WHERE "SurveyId" = $1;', [surveyId]);
        return questions.rows;
    } catch (error) {
        throw error;
    }
};


module.exports = {getQuestionsWithUserAnswers, getQuestionsForSurvey}