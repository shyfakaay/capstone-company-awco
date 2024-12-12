const express = require('express');
const router = express.Router();
const authController = require('../controllers/authController');
const upload = require('../middlewares/uploadMiddleware');

// Endpoint registrasi
router.post('/register', upload.single('photo'), authController.register);

// Endpoint login
router.post('/login', authController.login);

// Endpoint untuk mengambil daftar pengguna
router.get('/users', authController.getUsers);

// Endpoint untuk mengambil profil pengguna
router.get('/profile/:id', authController.getProfile);

module.exports = router;
