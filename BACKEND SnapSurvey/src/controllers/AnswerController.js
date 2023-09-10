const Answer  = require('../models/AnswerModel');
const { getQuestionsWithUserAnswers } = require('../models/QuestionModel');

exports.submitAnswer = async (req, res) => {
    const { answers } = req.body; 
  
    const result = await Answer.submitAnswer(answers);
  
    if (result.success) {
      res.status(201).json({ message: result.message });
    } else {
      res.status(500).json({ message: result.message, error: result.error });
    }
  };


  exports.getUserAnswers = async(req, res) => {
    const token = req.headers.authorization.split(' ')[1];

    try {

    const surveyId = req.body;

    const answers = await Answer.getUserAnswers(token, surveyId);

    const questionsWithUserAnswers = await getQuestionsWithUserAnswers(surveyId, answers);


        res.status(201).json(questionsWithUserAnswers);
    }
    catch(error){
        res.status(500).json({message: "An error occured while fetching data! "});
    }
  }
