const { Pool } = require('pg');

const pool = new Pool ({
    host: 'localhost',
    user: 'postgres',
    password: "knezevic10",
    database: 'SnapSurveyDatabase',
    port: 5432
});

  pool.connect((err, client, done) => {
    if (err) {
        console.error('Error connecting to database:', err);
        process.exit(1); // Izlaz iz aplikacije s greškom
    }
    console.log('Connected to database');
    done(); // Oslobađanje klijenta natrag u bazen
});

  module.exports = pool;