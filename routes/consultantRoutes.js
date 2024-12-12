const express = require('express');
const { getConsultants, addConsultant } = require('../controllers/consultantController');

const router = express.Router();

// Endpoint untuk mendapatkan daftar konsultan
router.get('/', getConsultants);

// Endpoint untuk menambahkan konsultan baru
router.post('/add', addConsultant);

module.exports = router;
