const express = require('express');
const router = express.Router();
const userController = require('../controllers/UserController');

// get all users
router.get('/', userController.getAllUsers);
//get user by id
router.get('/:id', userController.getUserById);
//create new user
router.post('/', userController.createNewUser);

router.delete('/:id', userController.deleteUser);


module.exports = router;