const express = require('express');
const router = express.Router();
const surveyAssignmentController = require('../controllers/SurveyAssignmentController');


router.get('/:surveyAssignmentId/questions', surveyAssignmentController.getSurveyQuestions);
router.get('/:surveyId/:userId', surveyAssignmentController.getSurveyAssignment)
router.put('/:surveyAssignmentId', surveyAssignmentController.updateSurveyAssignment);
router.get('/getAll', surveyAssignmentController.getAll)

module.exports = router;