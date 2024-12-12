const User = require('../models/User');

exports.verifyUser = async (req, res, next) => {
    try {
        const { userId } = req.body;
        const user = await User.findByPk(userId);
        if (!user) {
            return res.status(404).json({ message: 'User not found' });
        }
        req.user = user;
        next();
    } catch (error) {
        next(error);
    }
};
