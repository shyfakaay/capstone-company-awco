const { uploadToCloudStorage } = require('../utils/uploadUtils');
const Payment = require('../models/Payment');

exports.createPayment = async (req, res, next) => {
  try {
    const { amount, bookingId } = req.body;
    const payment = await Payment.create({
      amount,
      bookingId,
      status: 'pending'
    });

    res.status(201).json({ message: 'Payment created successfully', payment });
  } catch (error) {
    next(error);
  }
};

exports.uploadBuktiTransfer = async (req, res, next) => {
  try {
    const { paymentId } = req.params;
    const payment = await Payment.findByPk(paymentId);

    if (!payment) {
      return res.status(404).json({ message: 'Payment not found' });
    }

    const fileBuffer = req.file.buffer;
    const fileName = `bukti-transfer-${paymentId}-${Date.now()}`;
    const fileUrl = await uploadToCloudStorage(fileBuffer, fileName);

    payment.buktiTransfer = fileUrl;
    await payment.save();

    res.status(200).json({ message: 'Bukti transfer uploaded successfully', payment });
  } catch (error) {
    next(error);
  }
};

exports.updatePaymentStatus = async (req, res, next) => {
  try {
    const { paymentId } = req.params;
    const payment = await Payment.findByPk(paymentId);

    if (!payment) {
      return res.status(404).json({ message: 'Payment not found' });
    }

    payment.status = 'paid';
    await payment.save();

    res.status(200).json({ message: 'Payment status updated successfully', payment });
  } catch (error) {
    next(error);
  }
};