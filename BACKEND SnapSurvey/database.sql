ALTER TABLE Users
ADD COLUMN account_created DATETIME,
ADD COLUMN first_name VARCHAR(50),
ADD COLUMN last_name VARCHAR(50);

DESCRIBE Users;
ALTER TABLE Users MODIFY id INT NOT NULL AUTO_INCREMENT;
ALTER TABLE surveys DROP FOREIGN KEY user_id;
ALTER TABLE Users MODIFY id INT NOT NULL AUTO_INCREMENT, DROP PRIMARY KEY, ADD PRIMARY KEY (id);
ALTER TABLE Users DROP id;
ALTER TABLE Users MODIFY id INT AUTO_INCREMENT;
ALTER TABLE Surveys DROP FOREIGN KEY surveys_ibfk_1;
ALTER TABLE Answers DROP FOREIGN KEY answers_ibfk_1;
ALTER TABLE UserGroups DROP FOREIGN KEY usergroups_ibfk_1;
ALTER TABLE surveyassignments DROP FOREIGN KEY surveyassignments_ibfk_1;
ALTER TABLE surveyassignments DROP FOREIGN KEY surveyassignments_ibfk_2;
ALTER TABLE surveyassignments DROP FOREIGN KEY surveyassignments_ibfk_3;

ALTER TABLE Users MODIFY id INT AUTO_INCREMENT;

ALTER TABLE Surveys ADD CONSTRAINT surveys_ibfk_1 FOREIGN KEY (user_id) REFERENCES Users(id);
ALTER TABLE Answers ADD CONSTRAINT answers_ibfk_1 FOREIGN KEY (user_id) REFERENCES Users(id);
ALTER TABLE UserGroups ADD CONSTRAINT usergroups_ibfk_1 FOREIGN KEY (user_id) REFERENCES Users(id);
ALTER TABLE SurveyAssignments ADD CONSTRAINT surveyassignments_ibfk_1 FOREIGN KEY (survey_id) REFERENCES Surveys(id);
ALTER TABLE SurveyAssignments ADD CONSTRAINT surveyassignments_ibfk_2 FOREIGN KEY (user_id) REFERENCES Users(id);
ALTER TABLE SurveyAssignments ADD CONSTRAINT surveyassignments_ibfk_3 FOREIGN KEY (group_id) REFERENCES UserGroups(id);

ALTER TABLE Surveys ADD COLUMN IsActive bool;

INSERT INTO Surveys (id, title, description, user_surveyssurveyassignmentsid)
VALUES
  (1, 'Anketa 1', 'Opis ankete 1'),
  (2, 'Anketa 2', 'Opis ankete 2'),
  (3, 'Anketa 3', 'Opis ankete 3');
  
INSERT INTO SurveyAssignments (id, survey_id, user_id, group_id)
VALUES
  (1, 1, 2, NULL),
  (2, 2, 2, NULL) ;
  
SELECT *
FROM Surveys
WHERE user_id = 2;

ALTER TABLE Surveys ADD COLUMN user_id INT;
ALTER TABLE Surveys ADD CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES Users(id);

SELECT Surveys.*
FROM Surveys
JOIN Users ON Surveys.user_id = Users.id
WHERE Users.id = '2';

SELECT Surveys.*, SurveyAssignments.user_id
FROM Surveys
JOIN SurveyAssignments ON Surveys.id = SurveyAssignments.survey_id
WHERE SurveyAssignments.user_id = 4;

ALTER TABLE Surveyssurveyassignments
DROP FOREIGN KEY fk_user_id;

INSERT INTO SurveyAssignments (id, survey_id, user_id, group_id)
VALUES
  (5, 2, 4, NULL),
  (7, 3, 4, NULL);
  
  ALTER TABLE surveys
  DROP user_id
