exports.validateRequest = (req, res, next) => {
    const errors = [];
    for (const field in req.body) {
        if (!req.body[field]) {
            errors.push(`${field} is required`);
        }
    }
    if (errors.length) {
        return res.status(400).json({ message: 'Validation errors', errors });
    }
    next();
};
