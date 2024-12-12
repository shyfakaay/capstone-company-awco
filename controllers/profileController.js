const User = require('../models/User');
const Booking = require('../models/Booking');

exports.getProfile = async (req, res, next) => {
    try {
        const { userId } = req.body;
        const user = await User.findByPk(userId, {
            include: [{ model: Booking, as: 'bookings' }]
        });
        res.json(user);
    } catch (error) {
        next(error);
    }
};