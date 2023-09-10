const admin = require('firebase-admin');
const pool = require('../../config/database');
const firebase = require("firebase/app");
require("firebase/auth");

const SurveyAssignment = function(surveyAssignment){
    this.Id = surveyAssignment.Id;
    this.UserId = surveyAssignment.UserId;
    this.SurveyId = surveyAssignment.SurveyId;
    this.IsFilled = surveyAssignment.IsFilled;
    this.DateFilled = surveyAssignment.DateFilled;
    this.UserGroupId = surveyAssignment.UserGroupId;

}

SurveyAssignment.getAll = async () => {
    try{
        const result = await pool.query(`SELECT * FROM "SurveyAssignment"`)
        return result.rows
    }catch(error){
        throw error;
    }
}
SurveyAssignment.getSurveyQuestions = async (surveyAssignmentId) => {
    try {
        const questionsResult = await pool.query(`
      SELECT "Question"."Id", "Question"."QuestionText", "Question"."AnswerOptionIds", "Question"."IsAnswered", "Question"."SurveyId", "Question"."ImageUrl"
      FROM "Question"
      JOIN "SurveyAssignment" ON "Question"."SurveyId" = "SurveyAssignment"."SurveyId"
      WHERE "SurveyAssignment"."Id" = $1;
    `, [surveyAssignmentId]);
        return questionsResult.rows;
    } catch (error) {
        throw error;
    }
};

SurveyAssignment.getSurveyAssignment = async (surveyId, userId) => {
    try{
        const surveyAssignment = await pool.query(`SELECT * FROM "SurveyAssignment" WHERE "SurveyId" = $1 AND "UserId" = $2;`, [surveyId, userId] );

        if(surveyAssignment.rows.length > 0) {
            return surveyAssignment.rows[0];
        }else{
            return null
        }
    }catch(error){
        return error
    }
}

SurveyAssignment.updateSurveyAssignment = async (surveyAssignmentId) => {
    try {
        const currentDate = new Date();
        const query = `
            UPDATE "SurveyAssignment"
            SET "IsFilled" = true, "DateFilled" = $1
            WHERE "Id" = $2;
        `;
        await pool.query(query, [currentDate, surveyAssignmentId]);

        return { success: true, message: "Survey assignment updated successfully" };
    } catch (error) {
        console.error("Error updating survey assignment:", error);
        return { success: false, message: "An error occurred while updating survey assignment" };
    }
};



module.exports = SurveyAssignment;