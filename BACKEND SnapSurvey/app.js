const express = require("express");
const bodyParser = require("body-parser")
const app = express();
const userRoutes = require('./src/routes/UserRoute');
const authRoutes = require('./src/routes/AuthRoute');
const surveyRoutes = require('./src/routes/SurveyRoute');
const answerRoutes = require('./src/routes/AnswerRoute');
const surveyAssignmentRoutes = require('./src/routes/SurveyAssignmentRoute');
const questionRoutes = require('./src/routes/QuestionRoute');
const answerOptionRoutes = require('./src/routes/AnswerOptionRoute');

const port = process.env.APP_PORT || 3000;

app.use(express.json());
app.use(bodyParser.urlencoded({ extended: true }));
  
app.use('/api/auth', authRoutes);
//create user routes
app.use('/api/users', userRoutes);

app.use('/api/user', surveyRoutes, surveyAssignmentRoutes);

app.use('/api/user/survey/questions', answerRoutes);

app.use('/api', questionRoutes, answerOptionRoutes)

app.use('/api/answerOption', answerOptionRoutes)

app.use('/api/surveyAssignment', surveyAssignmentRoutes)



app.listen(port, () => {
    console.log("Server up and running on PORT: ", port);
});