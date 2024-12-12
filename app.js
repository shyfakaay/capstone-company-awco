const express = require('express');
const bodyParser = require('body-parser');
const session = require('express-session'); 
const authRoutes = require('./routes/authRoutes');
const consultantRoutes = require('./routes/consultantRoutes');
const bookingRoutes = require('./routes/bookingRoutes');
const profileRoutes = require('./routes/profileRoutes');
const paymentRoutes = require('./routes/paymentRoutes');
const { errorHandler } = require('./middlewares/errorMiddleware');

const app = express();

app.use(bodyParser.json());

// Konfigurasi session
app.use(
    session({
        secret: 'awco', 
        resave: false,
        saveUninitialized: true,
        cookie: { secure: false }, // Gunakan true jika menggunakan HTTPS
    })
);

// Routes
app.use('/auth', authRoutes);
app.use('/consultants', consultantRoutes);
app.use('/booking', bookingRoutes);
app.use('/profile', profileRoutes);
app.use('/payment', paymentRoutes);

// Error handler
app.use(errorHandler);

module.exports = app;
