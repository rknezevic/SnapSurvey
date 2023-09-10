const express = require('express');
const router = express.Router();

const { register, login, signOut } = require('../controllers/AuthController')

router.post("/register", register);
router.post("/login", login);
router.post("/signout", signOut);

module.exports = router;