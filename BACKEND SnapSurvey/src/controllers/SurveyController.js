const { getAvailableSurveys, getFilledSurveys, getSurveyById } = require("../models/SurveyModel")

exports.getAvailableSurveys = async (req, res) => {
    const token = req.headers.authorization.split("Bearer ")[1];
    console.log(token)
    try {
        const surveys = await getAvailableSurveys(token);
        res.status(201).json(surveys);

    } catch (error) {
        res.status(500).send(error.message);
    }
}

exports.getFilledSurveys = async (req, res) => {
    const token = req.headers.authorization.split("Bearer ")[1];
    try {
        const surveys = await getFilledSurveys(token);
        res.status(201).json(surveys);
    }
    catch (error) {
        res.status(500).send(error.message);
    }
}

exports.getSurveyById = async (req, res) => {
    const surveyId = req.params.surveyId;
    try {
        const survey = await getSurveyById(surveyId);
        res.status(200).json(survey);
    } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Internal server error' });
    }
};
