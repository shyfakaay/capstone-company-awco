const { initiatePayment, getPaymentStatus } = require('../utils/paymentUtils');
const Booking = require('../models/Booking');
const { DURASI_KONSULTASI, HARGA_KONSULTASI } = require('../utils/constants');

exports.createBooking = async (req, res, next) => {
  try {
      const { userId, consultantId, selectedDate, selectedTime } = req.body;

      console.log('Request Body:', req.body);
      console.log('Session Before:', req.session);

      const booking = await Booking.create({
          userId,
          consultantId,
          selectedDate,
          selectedTime,
          duration: DURASI_KONSULTASI,
          price: HARGA_KONSULTASI,
      });

      req.session.bookingId = booking.id;

      console.log('Session After:', req.session);

      res.status(201).json({ message: 'Booking created successfully', booking });
  } catch (error) {
      console.error('Error creating booking:', error);
      next(error);
  }
};


exports.getPaymentStatus = async (req, res, next) => {
  try {
    const transactionId = req.params.transactionId;
    const paymentStatus = await getPaymentStatus(transactionId);

    res.status(200).json({ message: 'Payment status retrieved successfully', paymentStatus });
  } catch (error) {
    next(error);
  }
};

exports.proceedToPayment = async (req, res, next) => {
  try {
    const bookingId = req.session.bookingId;
    const booking = await Booking.findByPk(bookingId);

    if (!booking) {
      return res.status(404).json({ message: 'Booking not found' });
    }

    // Buat transaksi pembayaran
    const payment = await initiatePayment(booking.price, booking.id);

    res.status(201).json({ message: 'Payment initiated successfully', payment });
  } catch (error) {
    next(error);
  }
};