const express = require('express');
const router = express.Router();
const surveyController = require('../controllers/SurveyController');

// get all users
router.get('/surveys', surveyController.getAvailableSurveys);

router.get('/filledSurveys', surveyController.getFilledSurveys);

router.get('/surveys/:surveyId', surveyController.getSurveyById)


module.exports = router;