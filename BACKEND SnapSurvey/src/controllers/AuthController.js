const admin = require('firebase-admin');
const bcrypt = require('bcryptjs');
const pool = require('../../config/database');
const serviceAccount = require("../../config/authentication/serviceAccount.json");
const firebase = require("firebase/app");
require("firebase/auth");

const firebaseConfig = {
    apiKey: "AIzaSyAkH0v73y5ThA1U5bLJVaJiEN6B_6vU154",
    authDomain: "snapsurvey-auth.firebaseapp.com",
    projectId: "snapsurvey-auth",
    storageBucket: "snapsurvey-auth.appspot.com",
    messagingSenderId: "678962715831",
    appId: "1:678962715831:web:5a6c702a26bf582c560132",
    measurementId: "G-PR3T29FD70"
};

firebase.initializeApp(firebaseConfig);

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount),
    databaseURL: 'https://<snapsurvey-auth>.firebaseio.com'
});


const register = async (req, res) => {
    const { email, password, firstName, lastName, username } = req.body;

    try {
        const userRecord = await admin.auth().createUser({
            email: email,
            password: password

        });

        const hashedPassword = await bcrypt.hash(password, 10);

        await pool.query('INSERT INTO \"User\" (\"Id\", \"FirstName\", \"LastName\", \"Username\", \"Email\", \"Password\") VALUES ($1, $2, $3, $4, $5, $6) RETURNING *', [userRecord.uid, firstName, lastName, username, email, hashedPassword]);

        res.status(201).json({ message: "User registered successfully!" });

    }
    catch (error) {
        console.error(error);
        res.status(500).json({ message: "Error registering user!" });
    }
};

const login = async (req, res) => {
    const { email, password } = req.body;
    try {
        const userCredential = await firebase.auth().signInWithEmailAndPassword(email, password);
        const user = userCredential.user;
        console.log(user)
        if (user) {
            
            const customToken = await admin.auth().createCustomToken(user.uid);
            const userId = user.uid
          
            
            console.log("User logged in successfully!");
            return res.status(200).json({ userId, customToken });
        }
    } catch (error) {
        console.error(error);
        res.status(401).json({ message: "Invalid credentials" });
    }
};

const signOut = async (req, res) => {
    const user = firebase.auth().currentUser;
    if (user) {
        firebase.auth().signOut().then(() => {
            console.log("User successfully logged out!");
            res.status(200).json({ message: "User successfully logged out" });
        }).catch(error => {
            console.log("Something went wrong", error);
            res.status(500).json({ message: "Error logging out" });
        });
    } else {
        res.status(403).json({
            status: 'failure',
            message: 'User already logged out'
        });
    }
};


module.exports = { register, login, signOut };