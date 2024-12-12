const express = require('express');
const router = express.Router();
const bookingController = require('../controllers/bookingController');

router.post('/', bookingController.createBooking);
router.get('/payment-status/:transactionId', bookingController.getPaymentStatus);
router.post('/proceed-to-payment', bookingController.proceedToPayment);

module.exports = router;