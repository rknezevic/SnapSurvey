// Import the functions you need from the SDKs you need
import { initializeApp } from "firebase/app";
import { getAnalytics } from "firebase/analytics";
// TODO: Add SDKs for Firebase products that you want to use
// https://firebase.google.com/docs/web/setup#available-libraries

// Your web app's Firebase configuration
// For Firebase JS SDK v7.20.0 and later, measurementId is optional
const firebaseConfig = {
  apiKey: "AIzaSyAkH0v73y5ThA1U5bLJVaJiEN6B_6vU154",
  authDomain: "snapsurvey-auth.firebaseapp.com",
  projectId: "snapsurvey-auth",
  storageBucket: "snapsurvey-auth.appspot.com",
  messagingSenderId: "678962715831",
  appId: "1:678962715831:web:5a6c702a26bf582c560132",
  measurementId: "G-PR3T29FD70"
};

// Initialize Firebase
const app = initializeApp(firebaseConfig);
const analytics = getAnalytics(app);