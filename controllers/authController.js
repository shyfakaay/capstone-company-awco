const User = require('../models/User');
const bcrypt = require('bcrypt');
const jwt = require('jsonwebtoken');
const { uploadToCloudStorage } = require('../utils/uploadUtils');

exports.register = async (req, res, next) => {
    try {
        const { username, email, password } = req.body;

        // Proses upload file foto
        let photoUrl = null;
        if (req.file) {
            const fileBuffer = req.file.buffer;
            const fileName = `users/${Date.now()}-${req.file.originalname}`;
            photoUrl = await uploadToCloudStorage(fileBuffer, fileName);
        }

        // Hash password
        const hashedPassword = await bcrypt.hash(password, 10);

        // Simpan user ke database
        const user = await User.create({ username, email, password: hashedPassword, photo: photoUrl });
        res.status(201).json({ message: 'User registered successfully', user });
    } catch (error) {
        next(error);
    }
};

exports.login = async (req, res, next) => {
    try {
        const { email, password } = req.body;

        // Mencari pengguna berdasarkan email
        const user = await User.findOne({ where: { email } });
        if (!user) {
            return res.status(404).json({ message: 'User  not found' });
        }

        // Memverifikasi password
        const isPasswordValid = await bcrypt.compare(password, user.password);
        if (!isPasswordValid) {
            return res.status(401).json({ message: 'Invalid password' });
        }

        // Membuat token JWT
        const token = jwt.sign({ id: user.id, email: user.email }, process.env.JWT_SECRET, { expiresIn: '1h' });

        res.status(200).json({ message: 'Login successful', token });
    } catch (error) {
        next(error);
    }
};

exports.getUsers = async (req, res, next) => {
    try {
        const users = await User.findAll(); // Mengambil semua pengguna
        res.status(200).json(users);
    } catch (error) {
        next(error);
    }
};

exports.getProfile = async (req, res, next) => {
    try {
        const userId = req.params.id; // Mengambil ID pengguna dari parameter
        const user = await User.findOne({ where: { id: userId } });
        if (!user) {
            return res.status(404).json({ message: 'User  not found' });
        }
        res.status(200).json(user);
    } catch (error) {
        next(error);
    }
};