const admin = require('firebase-admin');
const pool = require('../../config/database');
const firebase = require("firebase/app");
require("firebase/auth");

const getAvailableSurveys = async (token) => {
    currentUser = await firebase.auth().signInWithCustomToken(token);
    const idToken = await firebase.auth().currentUser.getIdToken();
    const decodedToken = await admin.auth().verifyIdToken(idToken);
    const userId = decodedToken.uid;
    console.log(userId)
    try {
        const userSurveys = await pool.query('SELECT DISTINCT "Survey".* FROM "Survey" JOIN "SurveyAssignment" ON "Survey"."Id" = "SurveyAssignment"."SurveyId" WHERE ("SurveyAssignment"."UserId" = $1 OR "SurveyAssignment"."UserGroupId" IN (SELECT "UserGroup"."Id" FROM "UserGroup" WHERE "UserGroup"."UserId" = $1))AND "SurveyAssignment"."IsFilled" = false;', [userId]);
        if(userSurveys.rows < 1) {
            console.log("Unfortanately this user does not have any surveys to fill!");
        }
        else{
            console.log("User surveys catched successfully");
            console.log(userSurveys.rows)
            return userSurveys.rows;
        }
        
    }
    catch (error) {
        console.log("Error while fetching user's surveys", error)
        throw error;
    }
};

const getFilledSurveys = async (token) => {
    currentUser = await firebase.auth().signInWithCustomToken(token);

    const idToken = await firebase.auth().currentUser.getIdToken();
    const decodedToken = await admin.auth().verifyIdToken(idToken)
    const userId = decodedToken.uid;
    try {
        const userSurveys = await pool.query('SELECT DISTINCT "Survey".* FROM "Survey" JOIN "SurveyAssignment" ON "Survey"."Id" = "SurveyAssignment"."SurveyId" WHERE ("SurveyAssignment"."UserId" = $1 OR "SurveyAssignment"."UserGroupId" IN (SELECT "UserGroup"."Id" FROM "UserGroup" WHERE "UserGroup"."UserId" = $1))AND "SurveyAssignment"."IsFilled" = true;', [userId]); 
        if(userSurveys.length === null){
            throw new Error("User did not fill any survey!");
        }else{
            console.log("Surveys fetched successfully!");
            return userSurveys.rows;
        }

    }
    catch(error){
        console.log("Error fetching filled surveys", error);
        throw error;

    }
}

const getSurveyById = async (surveyId) => {
    const survey = await pool.query ('SELECT * FROM "Survey" WHERE "Id" = $1;',[surveyId])
    console.log(surveyId)
  
    try {
        console.log("Evo anketa")
        console.log(survey.rows[0])
        return survey.rows[0];
    } catch (error) {
      throw error;
    }
  };
  

module.exports = { getAvailableSurveys, getFilledSurveys, getSurveyById}

