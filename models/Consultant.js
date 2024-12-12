const { DataTypes } = require('sequelize');
const sequelize = require('../config/database');
const { v4: uuidv4 } = require('uuid');

const Consultant = sequelize.define('Consultants', {
    id: {
        type: DataTypes.STRING,
        primaryKey: true,
        allowNull: false,
        unique: true,
        defaultValue: uuidv4() // Menghasilkan UUID secara otomatis
    },
    name: {
        type: DataTypes.STRING,
        allowNull: false,
    },
    expertise: {
        type: DataTypes.STRING,
        allowNull: false,
    },
    photo: {
        type: DataTypes.STRING,
        allowNull: true,
    },
}, {
    tableName: 'Consultants',
    timestamps: false
});

module.exports = Consultant;