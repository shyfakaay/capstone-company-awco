const multer = require('multer');

// Konfigurasi multer
const storage = multer.memoryStorage();
const upload = multer({ storage });

module.exports = upload;
