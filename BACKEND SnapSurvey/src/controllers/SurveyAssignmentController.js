const  SurveyAssignment = require("../models/SurveyAssignmentModel")

exports.getSurveyQuestions = async (req, res) => {
    const surveyAssignmentId = req.params.surveyAssignmentId;
    try {
        const questions = await SurveyAssignment.getSurveyQuestions(surveyAssignmentId);
        res.status(200).json(questions);
      } catch (error) {
        console.error(error);
        res.status(500).json({ message: 'Internal server error' });
      }
    };

exports.getAll = async(req, res) => {
  try{
    const surveyAssignments = await SurveyAssignment.getAll();
    res.status(200).json(surveyAssignments)
  }catch(error){
    console.error(error);
    res.status(500).json({ message : "Internal server error"});
  }
};

exports.updateSurveyAssignment = async(req, res) => {
  const surveyAssignmentId = req.params.surveyAssignmentId

try {
    const result = await SurveyAssignment.updateSurveyAssignment(surveyAssignmentId)

    res.status(201).json({ message: result.message });
  }
catch(error){
  console.error(error)
  res.status(500).json({ message : 'Internal server error '});
}
}

exports.getSurveyAssignment = async(req, res) => {
  const surveyId = req.params.surveyId
  const userId = req.params.userId
try{
  const surveyAssignment = await SurveyAssignment.getSurveyAssignment(surveyId, userId)

  res.status(200).json(surveyAssignment)

}catch(error){
  console.log(error)
  res.status(500).json({ message: 'Internal server error'})

}
}