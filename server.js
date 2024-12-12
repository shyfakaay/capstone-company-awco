const app = require('./app');
const sequelize = require('./config/database');

const PORT = process.env.PORT || 8080;

sequelize.sync()
    .then(() => {
        console.log('Database synchronized');
        app.listen(PORT, () => {
            console.log(`Server running on port ${PORT}`);
        });
    })
    .catch(err => {
        console.error('Database synchronization failed:', err);
    });
