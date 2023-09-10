const e = require('express');
var pool = require('../../config/database');
//const { use } = require('../routes/UserRoute');

var User = function (user) {
    this.id = user.Id;
    this.firstName = user.FirstName;
    this.lastName = user.LastName;
    this.username = user.Username;
    this.email = user.Email;
    this.password = user.Password;
    this.loginTime = new Date();
    this.logoutTime = new Date();
}

User.getAllUsers = async () => {
    try {
        const queryResult = await pool.query('SELECT * FROM \"User\"');
        console.log('Users fetched successfully!!');
        return queryResult.rows;
    } catch (error) {
        console.log("Error while getting users", error);
        throw error;
    }
}

//get user by id from DB
User.getUserById = async (id) => {
    try {
        const queryResult = await pool.query('SELECT * FROM \"User\" WHERE \"Id\"= $1', [id]);

        if (queryResult.rows.length === 0) {
            console.log("User with that id not found!");
            return null;
        }

        console.log("Users fetched successfully!");
        return queryResult.rows;

    } catch (error) {
        console.log("Error fetching data!");
        throw error;
    }
}

User.deleteUser = async (id) => {
    try {
        const queryResult = await pool.query('DELETE FROM "User" WHERE "Id" = $1 RETURNING *', [id]);

        if (queryResult.rows.length === 0) {
            console.log("User not found");
            return null;
        }
        return ("User deleted successfully!");

    } catch (error) {
        throw error;
    }
};


//create new user
User.createNewUser = async (request) => {
    const { firstName, lastName, username, email, password } = request.body;
    try {
        const emailCheckResult = await pool.query('SELECT * FROM "User" WHERE "Email" = $1', [email]);

        if (emailCheckResult.rows.length > 0) {
            throw new Error("Email already exists");
        }
        const queryResult = await pool.query('INSERT INTO \"User\" (\"FirstName\", \"LastName\", \"Username\", \"Email\", \"Password\") VALUES ($1, $2, $3, $4, $5) RETURNING *', [firstName, lastName, username, email, password]);
        console.log("User created successfully!");
        return queryResult.rows;
    } catch (error) {
        console.log("Error while inserting data", error);
        throw error;
    }
};


module.exports = User;
