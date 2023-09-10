const pool = require('../../config/database');


const getAnswerOption = async (answerOptionId) => {
try{
    const answerOption = await pool.query(`SELECT * FROM "AnswerOption" WHERE "Id" = $1`, [answerOptionId] )
    return answerOption.rows[0]
}
catch(error){
    throw(error)
}
}

module.exports = { getAnswerOption }