const express = require('express');
const router = express.Router();
const answerOptionController = require('../controllers/AnswerOptionController');

router.get('/:answerOptionId', answerOptionController.getAnswerOption)

module.exports = router;