const UserModel = require('../models/UserModel');

exports.getAllUsers = async (req, res) => {
    try {
        const users = await UserModel.getAllUsers();
        console.log("Users", users);
        res.send(users);
    } catch (error) {
        res.status(500).send(error.message);
    }
}


//create new user
exports.createNewUser = async (req, res) => {
    try {
        const createdUser = await UserModel.createNewUser(req);
        res.json({
            status: true,
            message: 'User created successfully!',
            data: createdUser
        });
    } catch (error) {
        res.status(500).json({
            status: false,
            message: 'Error creating user',
            error: error.message
        });
    }
}


//get user by id
exports.getUserById = async (req, res) => {
    try {
        const user = await UserModel.getUserById(req.params.id);

        if (user === null) {
            // User not found
            return res.status(404).send("User not found");
        }

        console.log("single user data", user);
        res.send(user);
    } catch (error) {
        res.status(500).send(error.message);
    }
}
//get users surveys
exports.getAvailableUserSurveys = async (req, res) => {
    const token = req.headers.authorization.split("Bearer ")[1];
    try {
        const surveys = await UserModel.getAvailableUserSurveys(token);
        
        if (surveys.length < 1)
            return res.status(404).send("User doesnt have any surveys");
            console.log(surveys);
        res.send(surveys);

    } catch (error) {
        res.status(500).send(error.message);
    }
}



exports.deleteUser = async (req, res) => {
    try {
        const deletedUser = await UserModel.deleteUser(req.params.id);

        if (deletedUser === null) {
            return res.status(404).send("User not found");
        }

        console.log("User deleted successfully:", deletedUser);
        res.send(deletedUser);
    } catch (error) {
        res.status(500).send(error.message);
    }
};




