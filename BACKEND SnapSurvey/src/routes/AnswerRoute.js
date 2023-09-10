const express = require('express');
const router = express.Router();
const answerController = require('../controllers/AnswerController');

router.post('/submit', answerController.submitAnswer);

router.get('/answers', answerController.getUserAnswers);

module.exports = router;
