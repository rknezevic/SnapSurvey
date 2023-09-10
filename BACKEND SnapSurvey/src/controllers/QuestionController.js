const {getQuestionsWithUserAnswers, getQuestionsForSurvey} = require('../models/QuestionModel');

exports.getQuestionsWithUserAnswers = async (req, res) => {
    const { surveyId } = req.params;

    try {
        const questionsWithUserAnswers = await QuestionModel.getQuestionsWithUserAnswers(surveyId);
        res.status(200).json(questionsWithUserAnswers);
    } catch (error) {
        res.status(500).json({ message: "An error occurred while fetching questions with user answers", error: error.message });
    }
};


exports.getQuestionsForSurvey = async (req, res) => {
    const { surveyId } = req.params; 

    try {
        const questions = await getQuestionsForSurvey(surveyId);
        res.status(200).json(questions);
    } catch (error) {
        console.error('Greška prilikom dohvaćanja pitanja za anketu:', error);
        res.status(500).json({ message: 'Greška prilikom dohvaćanja pitanja za anketu' });
    }
};

module.exports = {getQuestionsWithUserAnswers, getQuestionsForSurvey}