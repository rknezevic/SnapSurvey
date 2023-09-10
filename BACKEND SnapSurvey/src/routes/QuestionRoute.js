const express = require('express');
const router = express.Router();

const questionController = require("../controllers/QuestionController");

router.get("/:surveyId/questions", questionController.getQuestionsForSurvey)

module.exports = router;