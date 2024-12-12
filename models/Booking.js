const { DataTypes } = require('sequelize');
const sequelize = require('../config/database');
const User = require('./User');
const Consultant = require('./Consultant');

const Booking = sequelize.define(
    'Bookings',
    {
        id: {
            type: DataTypes.INTEGER,
            primaryKey: true,
            autoIncrement: true,
        },
        userId: {
            type: DataTypes.STRING,
            allowNull: false,
        },
        consultantId: {
            type: DataTypes.INTEGER,
            allowNull: false,
        },
        selectedDate: {
            type: DataTypes.DATEONLY,
            allowNull: false,
        },
        selectedTime: {
            type: DataTypes.TIME,
            allowNull: false,
        },
    },
    {
        timestamps: false, // Menonaktifkan createdAt dan updatedAt
    }
);

// Relasi dengan tabel User
Booking.belongsTo(User, { foreignKey: 'userId', as: 'user' });

// Relasi dengan tabel Consultant
Booking.belongsTo(Consultant, { foreignKey: 'consultantId', as: 'consultant' });

module.exports = Booking;
