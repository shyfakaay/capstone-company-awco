const express = require('express');
const paymentController = require('../controllers/paymentController');
const router = express.Router();
const upload = require('../middlewares/uploadMiddleware');

router.post('/pay', paymentController.createPayment);
router.post('/upload-bukti-transfer/:paymentId', upload.single('buktiTransfer'), paymentController.uploadBuktiTransfer);
router.put('/update-payment-status/:paymentId', paymentController.updatePaymentStatus);

module.exports = router;
