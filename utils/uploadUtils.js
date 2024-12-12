const { Storage } = require('@google-cloud/storage');
const path = require('path');

// Inisialisasi Google Cloud Storage
const storage = new Storage({
    projectId: 'capstone-awco',
    keyFilename: path.join(__dirname, './keyservice_bucket.json') // Path ke key file
});

const bucketName = 'awco-bucket';
const bucket = storage.bucket(bucketName);

/**
 * Fungsi untuk mengupload file ke Google Cloud Storage
 * @param {Buffer} fileBuffer - Buffer dari file yang akan diupload
 * @param {string} fileName - Nama file di bucket
 * @returns {string} - URL publik dari file
 */
const uploadToCloudStorage = async (fileBuffer, fileName) => {
    const file = bucket.file(fileName);

    await file.save(fileBuffer, {
        contentType: 'image/jpeg', // Sesuaikan dengan format file
        // Hapus public: true karena Uniform Bucket-Level Access mengelola akses
    });

    // URL file yang di-upload bisa diakses melalui URL biasa
    return `https://storage.googleapis.com/${bucketName}/${fileName}`;
};

module.exports = { uploadToCloudStorage };
