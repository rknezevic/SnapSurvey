const e = require('express');
var pool = require('../../config/database');
const admin = require('firebase-admin');
const firebase = require("firebase/app");
const uuid= require('uuid')

const Answer = function(answer) {
    this.UserId = answer.UserId;
    this.QuestionId = answer.QuestionId;
    this.AnswerText = answer.AnswerText;
    this.SelectedOptionId = answer.SelectedOptionId;
  };


  Answer.submitAnswer = async (answers) => {
    const idToken = await firebase.auth().currentUser.getIdToken();
    const decodedToken = await admin.auth().verifyIdToken(idToken);
    const userId = decodedToken.uid;
  
    try {
      await pool.query('BEGIN');
  
      for (const answer of answers) {
        const uuid_generate_v4 = uuid.v4();
        const { QuestionId, AnswerText, SelectedOptionId } = answer;
        console.log(answer)
        console.log("NEZ")
  
        await pool.query('INSERT INTO "Answer" ("Id", "UserId", "QuestionId", "AnswerText", "SelectedOptionId") VALUES ($1, $2, $3, $4, $5);',
          [uuid_generate_v4, userId, QuestionId, AnswerText, SelectedOptionId]);
        console.log(uuid_generate_v4)
  
        await pool.query('UPDATE "Question" SET "IsAnswered" = true WHERE "Id" = $1;', [QuestionId]);
      }
  
      await pool.query('COMMIT');
  
      return { success: true, message: "Answers submitted successfully" };
    } catch (error) {
      await pool.query('ROLLBACK');
      return { success: false, message: "An error occurred while submitting the answers", error: error.message };
    }
  };

  Answer.getUserAnswers = async (token, req, res) => {
    const surveyId = req.params.surveyId; // ID ankete
    const decodedToken = await admin.auth().verifyIdToken(token);
    const userId = decodedToken.uid;

    try {
        const answers = await pool.query('SELECT * FROM "Answer" WHERE "UserId" = $1 AND "SurveyId" = $2;', [userId, surveyId]);

       const answersByQuestion = {};
        answers.rows.forEach(answer => {
            if (!answersByQuestion[answer.QuestionId]) {
                answersByQuestion[answer.QuestionId] = [];
            }
            answersByQuestion[answer.QuestionId].push(answer);
        });

        const questions = await pool.query('SELECT * FROM "Question" WHERE "SurveyId" = $1;', [surveyId]);

        const questionsWithUserAnswers = questions.rows.map(question => ({
            ...question,
            userAnswers: answersByQuestion[question.Id] || [],
        }));

        res.status(200).json(questionsWithUserAnswers);
    } catch (error) {
        res.status(500).json({ message: "An error occurred while fetching user answers", error: error.message });
    }
};

  
module.exports = Answer;