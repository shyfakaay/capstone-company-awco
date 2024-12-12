const Consultant = require('../models/Consultant');

// Fungsi untuk mendapatkan daftar konsultan
exports.getConsultants = async (req, res, next) => {
    try {
        const consultants = await Consultant.findAll();
        res.json(consultants);
    } catch (error) {
        next(error);
    }
};

// Fungsi untuk menambahkan konsultan baru
exports.addConsultant = async (req, res, next) => {
    try {
        const { name, expertise, photo } = req.body;

        // Validasi input
        if (!name || !expertise) {
            return res.status(400).json({ message: 'Name and expertise are required' });
        }

        // Tambahkan konsultan ke database
        const newConsultant = await Consultant.create({
            name,
            expertise,
            photo: photo || null, // Photo bersifat opsional
        });

        res.status(201).json({
            message: 'Consultant added successfully',
            consultant: newConsultant,
        });
    } catch (error) {
        console.error('Error adding consultant:', error);
        next(error);
    }
};
