const { DataTypes } = require('sequelize');
const sequelize = require('../config/database');
const Booking = require('./Booking'); 

const Payment = sequelize.define('Payments', {
    id: {
        type: DataTypes.INTEGER,
        primaryKey: true,
        autoIncrement: true,
    },
    amount: {
        type: DataTypes.DECIMAL(10, 2), 
        allowNull: false,
    },
    status: {
        type: DataTypes.STRING,
        defaultValue: 'pending',
    },
    buktiTransfer: {
        type: DataTypes.STRING,
        allowNull: true,
    },
    bookingId: {
        type: DataTypes.INTEGER,
        allowNull: false,
        references: {
            model: Booking,
            key: 'id',
        },
    },
}, {
    tableName: 'Payments',
    timestamps: false
});

module.exports = Payment;
